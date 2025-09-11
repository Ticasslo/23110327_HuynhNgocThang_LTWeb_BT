package ngocthang.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet(urlPatterns = { "/admin/category/list", "/manager/category/list", "/user/category/list" })
public class CategoryListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User currentUser = SessionUtils.getUser(req);
		List<Category> cateList;
		
		if (SessionUtils.isAdmin(req)) {
			// Admin xem tất cả categories
			cateList = cateService.getAll();
		} else if (SessionUtils.isManager(req)) {
			// Manager chỉ xem categories của mình
			cateList = cateService.getCategoriesByUserId(currentUser.getId());
		} else {
			// User xem tất cả categories (read-only)
			cateList = cateService.getAll();
		}
		
		req.setAttribute("cateList", cateList);
		req.setAttribute("currentUser", currentUser);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/list-category.jsp");
		dispatcher.forward(req, resp);
	}
}