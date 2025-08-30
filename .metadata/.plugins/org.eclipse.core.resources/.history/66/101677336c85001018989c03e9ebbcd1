package ngocthang.controller;

import java.io.IOException;
import java.security.SecureRandom;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.models.User;
import ngocthang.service.UserService;
import ngocthang.service.impl.UserServiceImpl;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {

	private UserService userService = new UserServiceImpl();

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
	 * Bước 2: Xác thực email và phone, sau đó đặt lại mật khẩu
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

		// Tạo mật khẩu mới
		String newPassword = generateNewPassword();

		// Cập nhật mật khẩu trong database
		boolean updated = userService.updatePassword(user.getUserName(), newPassword);

		if (updated) {
			req.setAttribute("alert", "Đặt lại mật khẩu thành công! Mật khẩu mới của bạn là: " + "<strong>"
					+ newPassword + "</strong><br>" + "Vui lòng đăng nhập lại");
		} else {
			req.setAttribute("error", "Có lỗi xảy ra trong quá trình đặt lại mật khẩu. Vui lòng thử lại sau.");
			req.setAttribute("step", 2);
			req.setAttribute("username", username);
		}

		req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
	}

	/**
	 * Tạo mật khẩu mới ngẫu nhiên
	 */
	private String generateNewPassword() {
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCase = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";
		String specialChars = "@#$%";

		String allChars = upperCase + lowerCase + digits + specialChars;
		SecureRandom random = new SecureRandom();
		StringBuilder newPassword = new StringBuilder();

		// Đảm bảo có ít nhất 1 ký tự mỗi loại
		newPassword.append(upperCase.charAt(random.nextInt(upperCase.length())));
		newPassword.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
		newPassword.append(digits.charAt(random.nextInt(digits.length())));
		newPassword.append(specialChars.charAt(random.nextInt(specialChars.length())));

		// Tạo thêm 4 ký tự ngẫu nhiên
		for (int i = 4; i < 8; i++) {
			newPassword.append(allChars.charAt(random.nextInt(allChars.length())));
		}

		// Xáo trộn mật khẩu
		return shuffleString(newPassword.toString());
	}

	/**
	 * Xáo trộn chuỗi
	 */
	private String shuffleString(String string) {
		char[] chars = string.toCharArray();
		SecureRandom random = new SecureRandom();

		for (int i = chars.length - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			char temp = chars[i];
			chars[i] = chars[j];
			chars[j] = temp;
		}

		return new String(chars);
	}
}