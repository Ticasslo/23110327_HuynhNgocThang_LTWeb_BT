package ngocthang.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.Constant;
import ngocthang.utils.SessionUtils;
import ngocthang.utils.CookieUtils;
import ngocthang.service.UserService;
import ngocthang.service.impl.UserServiceImpl;
import ngocthang.models.User;

import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Kiểm tra đã đăng nhập chưa
		if (SessionUtils.isLoggedIn(req)) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}

		// Check cookie remember me - tự động đăng nhập nếu có
		String rememberUsername = CookieUtils.getRememberMeUsername(req);
		if (rememberUsername != null) {
			UserService service = new UserServiceImpl();
			User user = service.get(rememberUsername);
			if (user != null) {
				SessionUtils.setUser(req, user);
				resp.sendRedirect(req.getContextPath() + "/waiting");
				return;
			}
		}
		
		req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");

		UserService service = new UserServiceImpl();
		String alertMsg = "";

		// Check email tồn tại
		if (service.checkExistEmail(email)) {
			alertMsg = "Email đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}

		// Check username tồn tại
		if (service.checkExistUsername(username)) {
			alertMsg = "Tài khoản đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
			return;
		}

		// Đăng ký
		boolean isSuccess = service.register(email, password, username, fullname, phone);
		if (isSuccess) {
			// SendMail sm = new SendMail();
			// sm.sendMail(email, "MyApp", "Welcome to My Shopping app. Please login!");

			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			alertMsg = "System error!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
		}
	}
}
