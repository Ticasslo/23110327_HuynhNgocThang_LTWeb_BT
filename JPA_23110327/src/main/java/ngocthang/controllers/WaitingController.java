package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.User;
import ngocthang.utils.SessionUtils;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionUtils.isLoggedIn(req)) {
			User u = SessionUtils.getUser(req);
			req.setAttribute("username", u.getUserName());
			
			if (SessionUtils.isAdmin(req)) {
				resp.sendRedirect(req.getContextPath() + "/admin/home");
			} else if (SessionUtils.isManager(req)) {
				resp.sendRedirect(req.getContextPath() + "/manager/home");
			} else if (SessionUtils.isUser(req)) {
				resp.sendRedirect(req.getContextPath() + "/user/home");
			} else {
				// Role không xác định - về login để đăng nhập lại
				resp.sendRedirect(req.getContextPath() + "/login");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}
}
