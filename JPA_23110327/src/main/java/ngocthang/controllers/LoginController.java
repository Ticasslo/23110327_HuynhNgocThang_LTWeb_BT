package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.User;
import ngocthang.services.IUserService;
import ngocthang.services.impl.UserServiceImpl;
import ngocthang.utils.CookieUtils;
import ngocthang.utils.SessionUtils;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Kiểm tra đã đăng nhập chưa
		if (SessionUtils.isLoggedIn(req)) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}

		// Check cookie remember me
		String rememberUsername = CookieUtils.getRememberMeUsername(req);
		if (rememberUsername != null) {
			// Tự động đăng nhập từ cookie
			// Truy vấn database để lấy đầy đủ thông tin user
			IUserService service = new UserServiceImpl();
			User user = service.get(rememberUsername); // Lấy User từ DB, KHÔNG từ cookie

			if (user != null) {
				SessionUtils.setUser(req, user);
				resp.sendRedirect(req.getContextPath() + "/waiting");
				return;
			}
		}

		// Hiển thị trang đăng nhập
		req.getRequestDispatcher("views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean isRememberMe = "on".equals(req.getParameter("remember"));

		String alertMsg = "";

		// Validate input
		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		// Xử lý đăng nhập
		IUserService service = new UserServiceImpl();
		User user = service.login(username.trim(), password);

		if (user != null) {
			// Đăng nhập thành công
			SessionUtils.setUser(req, user);

			// Lưu cookie remember me nếu được chọn
			if (isRememberMe) {
				CookieUtils.saveRememberMe(resp, username);
			}

			// Kiểm tra có URL redirect từ filter không
			String redirectUrl = SessionUtils.getRedirectUrl(req);
			if (redirectUrl != null) {
				SessionUtils.removeRedirectUrl(req);
				resp.sendRedirect(req.getContextPath() + redirectUrl);
			} else {
				// Redirect mặc định
				resp.sendRedirect(req.getContextPath() + "/waiting");
			}

		} else {
			// Đăng nhập thất bại
			alertMsg = "Tài khoản hoặc mật khẩu không đúng";
			req.setAttribute("alert", alertMsg);
			req.setAttribute("username", username); // Giữ lại username đã nhập
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
		}
	}
}
