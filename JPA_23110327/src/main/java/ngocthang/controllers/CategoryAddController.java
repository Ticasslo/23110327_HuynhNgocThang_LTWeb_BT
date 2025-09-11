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

@WebServlet(urlPatterns = { "/admin/category/add", "/manager/category/add" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 50,      // 50 MB (tăng để không bị reject)
    maxRequestSize = 1024 * 1024 * 60    // 60 MB (tăng để không bị reject)
)
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ICategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Thêm currentUser để JSP có thể xác định role
        User currentUser = SessionUtils.getUser(req);
        req.setAttribute("currentUser", currentUser);
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-add.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Khai báo currentUser ở đầu method để sử dụng trong catch blocks
        User currentUser = SessionUtils.getUser(req);
        
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8");

            String catename = UploadUtils.getFieldValue(req, "name");
            String icon = UploadUtils.uploadFile(req, "icon", "category");

            if (catename == null || catename.trim().isEmpty()) {
                req.setAttribute("error", "Tên danh mục không được để trống!");
                req.setAttribute("currentUser", currentUser);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-add.jsp");
                dispatcher.forward(req, resp);
                return;
            }

            // Tạo Category object
            Category category = new Category();
            category.setName(catename.trim());
            category.setIcon(icon);
            category.setUserid(currentUser.getId());

            // Lưu vào database
            cateService.insert(category);

            // Redirect về trang danh sách theo vai trò
            if (ngocthang.utils.SessionUtils.isManager(req)) {
                resp.sendRedirect(req.getContextPath() + "/manager/category/list");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            req.setAttribute("currentUser", currentUser);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category-add.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
