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

		String stepParam = req.getParameter("step");
		int step = (stepParam != null) ? Integer.parseInt(stepParam) : 1;

		if (step == 1) {
			handleStep1(req, resp);
		} else if (step == 2) {
			handleStep2(req, resp);
		} else if (step == 3) {
			handleStep3(req, resp);
		}
	}

	/**
	 * Bước 1: Xác thực username và hiển thị form nhập email/phone
	 */
	private void handleStep1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");

		if (username == null || username.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập tên tài khoản");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Tìm user theo username
		User user = userService.get(username.trim());

		if (user == null) {
			req.setAttribute("error", "Không tìm thấy tài khoản với tên: " + username);
			req.setAttribute("step", 1);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Chuyển sang bước 2
		req.setAttribute("step", 2);
		req.setAttribute("username", username);
		req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
	}

	/**
	 * Bước 2: Xác thực email và phone
	 */
	private void handleStep2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");

		// Validate input
		if (username == null || username.trim().isEmpty()) {
			req.setAttribute("error", "Thông tin tài khoản không hợp lệ");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (email == null || email.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập email xác thực");
			req.setAttribute("step", 2);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (phone == null || phone.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập số điện thoại xác thực");
			req.setAttribute("step", 2);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Tìm user và xác thực thông tin
		User user = userService.get(username.trim());

		if (user == null) {
			req.setAttribute("error", "Tài khoản không tồn tại");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Xác thực email và phone
		boolean emailMatch = email.trim().equalsIgnoreCase(user.getEmail());
		boolean phoneMatch = phone.trim().equals(user.getPhone());

		if (!emailMatch && !phoneMatch) {
			req.setAttribute("error", "Email và số điện thoại không khớp với thông tin tài khoản");
			req.setAttribute("step", 2);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (!emailMatch) {
			req.setAttribute("error", "Email không khớp với thông tin tài khoản");
			req.setAttribute("step", 2);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (!phoneMatch) {
			req.setAttribute("error", "Số điện thoại không khớp với thông tin tài khoản");
			req.setAttribute("step", 2);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Chuyển sang bước 3: cho phép người dùng tự tạo mật khẩu mới
		req.setAttribute("step", 3);
		req.setAttribute("username", username);
		req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
	}

	/**
	 * Bước 3: Người dùng nhập mật khẩu mới
	 */
	private void handleStep3(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String newPassword = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmPassword");

		// Validate input
		if (username == null || username.trim().isEmpty()) {
			req.setAttribute("error", "Thông tin tài khoản không hợp lệ");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (newPassword == null || newPassword.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập mật khẩu mới");
			req.setAttribute("step", 3);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng xác nhận mật khẩu mới");
			req.setAttribute("step", 3);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Kiểm tra mật khẩu có khớp nhau không
		if (!newPassword.equals(confirmPassword)) {
			req.setAttribute("error", "Mật khẩu xác nhận không khớp");
			req.setAttribute("step", 3);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Validate mật khẩu mạnh
		String passwordValidationError = validatePasswordStrength(newPassword);
		if (passwordValidationError != null) {
			req.setAttribute("error", passwordValidationError);
			req.setAttribute("step", 3);
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		// Cập nhật mật khẩu trong database
		boolean updated = userService.updatePassword(username.trim(), newPassword);

		if (updated) {
			req.setAttribute("alert", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập lại với mật khẩu mới.");
		} else {
			req.setAttribute("error", "Có lỗi xảy ra trong quá trình đặt lại mật khẩu. Vui lòng thử lại sau.");
			req.setAttribute("step", 3);
			req.setAttribute("username", username);
		}

		req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
	}

	/**
	 * Validate độ mạnh của mật khẩu
	 */
	private String validatePasswordStrength(String password) {
		if (password.length() < 6) {
			return "Mật khẩu phải có ít nhất 6 ký tự";
		}

		if (password.length() > 20) {
			return "Mật khẩu không được quá 20 ký tự";
		}

		boolean hasUpper = false;
		boolean hasLower = false;
		boolean hasDigit = false;
		boolean hasSpecial = false;

		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				hasUpper = true;
			} else if (Character.isLowerCase(c)) {
				hasLower = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else if ("@#$%&*!?".indexOf(c) >= 0) {
				hasSpecial = true;
			}
		}

		if (!hasUpper) {
			return "Mật khẩu phải có ít nhất 1 chữ hoa";
		}

		if (!hasLower) {
			return "Mật khẩu phải có ít nhất 1 chữ thường";
		}

		if (!hasDigit) {
			return "Mật khẩu phải có ít nhất 1 chữ số";
		}

		if (!hasSpecial) {
			return "Mật khẩu phải có ít nhất 1 ký tự đặc biệt (@#$%&*!?)";
		}

		return null; // Mật khẩu hợp lệ
	}
}
