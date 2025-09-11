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

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {

	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Reset về bước 1
		req.removeAttribute("step");
		req.removeAttribute("username");
		req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String step = req.getParameter("step");
		String alertMsg = "";

		if ("1".equals(step)) {
			// Bước 1: Nhập username/email
			String identifier = req.getParameter("identifier");
			if (identifier == null || identifier.trim().isEmpty()) {
				alertMsg = "Vui lòng nhập username hoặc email";
				req.setAttribute("alert", alertMsg);
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
				return;
			}

			// Tìm user theo username hoặc email
			User user = userService.findByUsernameOrEmail(identifier.trim());
			if (user == null) {
				alertMsg = "Không tìm thấy tài khoản với username/email: " + identifier;
				req.setAttribute("alert", alertMsg);
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
				return;
			}

			// Chuyển sang bước 2
			req.setAttribute("step", "2");
			req.setAttribute("username", user.getUserName());
			req.setAttribute("email", user.getEmail());
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);

		} else if ("2".equals(step)) {
			// Bước 2: Nhập mật khẩu mới
			String username = req.getParameter("username");
			String newPassword = req.getParameter("newPassword");
			String confirmPassword = req.getParameter("confirmPassword");

			if (newPassword == null || newPassword.trim().isEmpty()) {
				alertMsg = "Vui lòng nhập mật khẩu mới";
				req.setAttribute("alert", alertMsg);
				req.setAttribute("step", "2");
				req.setAttribute("username", username);
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
				return;
			}

			if (!newPassword.equals(confirmPassword)) {
				alertMsg = "Mật khẩu xác nhận không khớp";
				req.setAttribute("alert", alertMsg);
				req.setAttribute("step", "2");
				req.setAttribute("username", username);
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
				return;
			}

			// Cập nhật mật khẩu
			boolean success = userService.updatePassword(username, newPassword);
			if (success) {
				alertMsg = "Đổi mật khẩu thành công! Vui lòng đăng nhập lại.";
				req.setAttribute("alert", alertMsg);
				req.setAttribute("success", true);
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			} else {
				alertMsg = "Có lỗi xảy ra khi cập nhật mật khẩu";
				req.setAttribute("alert", alertMsg);
				req.setAttribute("step", "2");
				req.setAttribute("username", username);
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			}
		}
	}
}
