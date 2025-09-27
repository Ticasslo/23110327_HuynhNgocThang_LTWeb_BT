package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.User;
import ngocthang.services.IUserService_23110327;
import ngocthang.services.impl.UserServiceImpl_23110327;
import ngocthang.utils.Constant_23110327;
import ngocthang.utils.CookieUtils_23110327;
import ngocthang.utils.SessionUtils_23110327;

@WebServlet(urlPatterns = {"/login"})
public class LoginController_23110327 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final IUserService_23110327 iUserService = new UserServiceImpl_23110327();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (SessionUtils_23110327.isLoggedIn(req)) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}

		String rememberEmail = CookieUtils_23110327.getRememberMeUsername(req);
		if (rememberEmail != null) {
			User user = iUserService.getByUsernameOrEmail(rememberEmail);
			if (user != null) {
				SessionUtils_23110327.setUser(req, user);
				resp.sendRedirect(req.getContextPath() + "/waiting");
				return;
			}
		}

		req.getRequestDispatcher(Constant_23110327.Path.LOGIN).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		if (email == null || email.isBlank() || password == null || password.isBlank()) {
			req.setAttribute("alert", "Email hoặc mật khẩu không được rỗng");
			req.getRequestDispatcher(Constant_23110327.Path.LOGIN).forward(req, resp);
			return;
		}

		User user = iUserService.login(email.trim(), password);
		if (user != null) {
			// Cập nhật lastLogin
			user.setLastLogin(java.time.LocalDateTime.now());
			iUserService.save(user);
			
			SessionUtils_23110327.setUser(req, user);
			if ("on".equals(remember)) {
				CookieUtils_23110327.saveRememberMe(resp, email.trim());
			}
			String redirectUrl = SessionUtils_23110327.getRedirectUrl(req);
			if (redirectUrl != null) {
				SessionUtils_23110327.removeRedirectUrl(req);
				resp.sendRedirect(req.getContextPath() + redirectUrl);
			} else {
				resp.sendRedirect(req.getContextPath() + "/waiting");
			}
			return;
		}

		req.setAttribute("alert", "Email hoặc mật khẩu không đúng");
		req.setAttribute("email", email);
		req.getRequestDispatcher(Constant_23110327.Path.LOGIN).forward(req, resp);
	}
}


