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
import ngocthang.service.CategoryService;
import ngocthang.service.impl.CategoryServiceImpl;
import ngocthang.utils.UploadUtils;

@WebServlet(urlPatterns = { "/admin/category/add" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 50,      // 50 MB (tăng để không bị reject)
    maxRequestSize = 1024 * 1024 * 60    // 60 MB (tăng để không bị reject)
)
public class CategoryAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    CategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/add-category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        try {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            req.setCharacterEncoding("UTF-8");

            Category category = new Category();
            
            // Lấy tên category
            String categoryName = UploadUtils.getFieldValue(req, "name");
            category.setName(categoryName);
            
            // Validate và upload file icon
            String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "webp"};
            String validationError = UploadUtils.validateUpload(req, "icon", 5, allowedExtensions);
            
            if (validationError != null) {
                req.setAttribute("error", validationError);
                doGet(req, resp);
                return;
            }
            
            // Upload file icon
            String iconPath = UploadUtils.uploadFile(req, "icon", "category");
            category.setIcon(iconPath);
            
            // Lưu category
            cateService.insert(category);
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
            
        } catch (IllegalStateException e) {
            // Xử lý lỗi file quá lớn từ Tomcat
            if (e.getMessage() != null && e.getMessage().contains("SizeLimitExceededException")) {
                req.setAttribute("error", "File không được quá 5MB. Vui lòng chọn file nhỏ hơn.");
            } else {
                req.setAttribute("error", "File quá lớn hoặc không hợp lệ. Vui lòng thử lại.");
            }
            doGet(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            doGet(req, resp);
        }
    }
}