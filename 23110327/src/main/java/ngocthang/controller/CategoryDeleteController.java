package ngocthang.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ngocthang.models.Category;
import ngocthang.models.User;
import ngocthang.service.CategoryService;
import ngocthang.service.impl.CategoryServiceImpl;
import ngocthang.utils.SessionUtils;

@WebServlet(urlPatterns = { "/admin/category/delete", "/manager/category/delete" })
public class CategoryDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Category category = cateService.get(Integer.parseInt(id));
		User currentUser = SessionUtils.getUser(req);
		
		// Kiểm tra quyền xóa
		if (!SessionUtils.isAdmin(req) && category.getUserid() != currentUser.getId()) {
			// Manager chỉ được xóa category của mình
			if (SessionUtils.isManager(req)) {
				resp.sendRedirect(req.getContextPath() + "/manager/category/list?error=no_permission");
			} else {
				resp.sendRedirect(req.getContextPath() + "/admin/category/list?error=no_permission");
			}
			return;
		}
		
		cateService.delete(Integer.parseInt(id));
		
		// Redirect về đúng trang theo role
		if (SessionUtils.isManager(req)) {
			resp.sendRedirect(req.getContextPath() + "/manager/category/list");
		} else {
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
		}
	}
}