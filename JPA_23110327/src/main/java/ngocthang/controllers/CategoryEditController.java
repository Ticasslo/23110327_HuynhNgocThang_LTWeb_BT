package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.Category;
import ngocthang.entity.User;
import ngocthang.services.ICategoryService;
import ngocthang.services.impl.CategoryServiceImpl;
import ngocthang.utils.SessionUtils;
import ngocthang.utils.UploadUtils;

@WebServlet(urlPatterns = { "/admin/category/edit", "/manager/category/edit" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 50,      // 50 MB (tăng để không bị reject)
    maxRequestSize = 1024 * 1024 * 60    // 60 MB (tăng để không bị reject)
)
public class CategoryEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ICategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Category category = cateService.get(Integer.parseInt(id));
		
		// Kiểm tra quyền truy cập
		User currentUser = SessionUtils.getUser(req);
		if (!SessionUtils.isAdmin(req) && category.getUserid() != currentUser.getId()) {
			// Manager chỉ được edit category của mình
			if (SessionUtils.isManager(req)) {
				resp.sendRedirect(req.getContextPath() + "/manager/category/list?error=no_permission");
			} else {
				resp.sendRedirect(req.getContextPath() + "/admin/category/list?error=no_permission");
			}
			return;
		}
		
		req.setAttribute("category", category);
		req.setAttribute("currentUser", currentUser);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User currentUser = SessionUtils.getUser(req);
		
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");

			String idStr = UploadUtils.getFieldValue(req, "id");
			String catename = UploadUtils.getFieldValue(req, "name");

			if (idStr == null || idStr.trim().isEmpty()) {
				req.setAttribute("error", "ID danh mục không hợp lệ!");
				req.setAttribute("currentUser", currentUser);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
				dispatcher.forward(req, resp);
				return;
			}

			if (catename == null || catename.trim().isEmpty()) {
				req.setAttribute("error", "Tên danh mục không được để trống!");
				req.setAttribute("currentUser", currentUser);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
				dispatcher.forward(req, resp);
				return;
			}

			int id = Integer.parseInt(idStr);
			Category category = cateService.get(id);
			
			if (category == null) {
				req.setAttribute("error", "Không tìm thấy danh mục!");
				req.setAttribute("currentUser", currentUser);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
				dispatcher.forward(req, resp);
				return;
			}

			// Kiểm tra quyền sửa
			if (!SessionUtils.isAdmin(req) && category.getUserid() != currentUser.getId()) {
				req.setAttribute("error", "Bạn không có quyền sửa danh mục này!");
				req.setAttribute("currentUser", currentUser);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
				dispatcher.forward(req, resp);
				return;
			}

			// Lấy thông tin category cũ để giữ userid
			Category oldCategory = cateService.get(id);
			
			// Validate file icon (chỉ validate nếu có file mới)
			String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "webp", "jfif"};
			String validationError = UploadUtils.validateUpload(req, "icon", 5, allowedExtensions);
			
			if (validationError != null) {
				req.setAttribute("error", validationError);
				req.setAttribute("category", oldCategory);
				req.setAttribute("currentUser", currentUser);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
				dispatcher.forward(req, resp);
				return;
			}
			
			// Upload file icon mới (nếu có)
			String newIconPath = UploadUtils.uploadFile(req, "icon", "category");
			if (newIconPath != null) {
				// Xóa icon cũ nếu có
				if (oldCategory.getIcon() != null) {
					UploadUtils.deleteFile(oldCategory.getIcon());
				}
				category.setIcon(newIconPath);
			} else {
				// Giữ icon cũ nếu không upload file mới
				category.setIcon(oldCategory.getIcon());
			}
			
			// Cập nhật thông tin
			category.setName(catename.trim());
			
			// Xử lý userid theo logic tham khảo
			if (SessionUtils.isAdmin(req)) {
				// Admin KHÔNG claim ownership, giữ nguyên owner cũ
				category.setUserid(oldCategory.getUserid());
			} else {
				// Manager sửa category của mình, giữ nguyên ownership
				category.setUserid(currentUser.getId());
			}

			// Lưu vào database
			cateService.edit(category);

			// Redirect về trang danh sách
			if (SessionUtils.isManager(req)) {
				resp.sendRedirect(req.getContextPath() + "/manager/category/list");
			} else {
				resp.sendRedirect(req.getContextPath() + "/admin/category/list");
			}

		} catch (IllegalStateException e) {
			// Xử lý lỗi file quá lớn từ Tomcat
			if (e.getMessage() != null && e.getMessage().contains("SizeLimitExceededException")) {
				req.setAttribute("error", "File không được quá 5MB. Vui lòng chọn file nhỏ hơn.");
			} else {
				req.setAttribute("error", "File quá lớn hoặc không hợp lệ. Vui lòng thử lại.");
			}
			req.setAttribute("currentUser", currentUser);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
			req.setAttribute("currentUser", currentUser);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-edit.jsp");
			dispatcher.forward(req, resp);
		}
	}
}
