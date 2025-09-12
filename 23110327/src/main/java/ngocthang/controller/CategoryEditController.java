package ngocthang.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ngocthang.models.Category;
import ngocthang.models.User;
import ngocthang.service.CategoryService;
import ngocthang.service.impl.CategoryServiceImpl;
import ngocthang.utils.UploadUtils;
import ngocthang.utils.SessionUtils;

@WebServlet(urlPatterns = { "/admin/category/edit", "/manager/category/edit" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 50,      // 50 MB (tăng để không bị reject)
    maxRequestSize = 1024 * 1024 * 60    // 60 MB (tăng để không bị reject)
)
public class CategoryEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();

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
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/edit-category.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");

			Category category = new Category();
			
			// Lấy ID category
			String categoryId = UploadUtils.getFieldValue(req, "id");
			category.setId(Integer.parseInt(categoryId));
			
			// Lấy tên category
			String categoryName = UploadUtils.getFieldValue(req, "name");
			category.setName(categoryName);
			
			// Lấy thông tin category cũ để giữ icon nếu không upload file mới
			Category oldCategory = cateService.get(category.getId());
			
			// Kiểm tra quyền sửa
			User currentUser = SessionUtils.getUser(req);
			if (!SessionUtils.isAdmin(req) && oldCategory.getUserid() != currentUser.getId()) {
				req.setAttribute("error", "Bạn không có quyền sửa category này");
				req.setAttribute("category", oldCategory);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/edit-category.jsp");
				dispatcher.forward(req, resp);
				return;
			}
			
			// Xử lý userid
			if (SessionUtils.isAdmin(req)) {
				// Admin KHÔNG claim ownership, giữ nguyên owner cũ
				category.setUserid(oldCategory.getUserid());
			} else {
				// Manager sửa category của mình, giữ nguyên ownership
				category.setUserid(currentUser.getId());
			}
			
			// Validate và upload file icon (nếu có)
			String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "webp", "jfif"};
			String validationError = UploadUtils.validateUpload(req, "icon", 5, allowedExtensions);
			
			if (validationError != null) {
				req.setAttribute("error", validationError);
				req.setAttribute("category", oldCategory);
				req.setAttribute("currentUser", currentUser);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/edit-category.jsp");
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
			
			// Cập nhật category
			cateService.edit(category);
			
			// Redirect về đúng trang theo role
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
			// Thêm currentUser để JSP có thể xác định role
			User currentUser = SessionUtils.getUser(req);
			req.setAttribute("currentUser", currentUser);
			doGet(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
			// Thêm currentUser để JSP có thể xác định role
			User currentUser = SessionUtils.getUser(req);
			req.setAttribute("currentUser", currentUser);
			doGet(req, resp);
		}
	}
}