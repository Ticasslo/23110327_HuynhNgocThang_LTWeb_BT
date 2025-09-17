package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.User;
import ngocthang.services.IUserService;
import ngocthang.services.impl.UserServiceImpl;
import ngocthang.utils.Constant;
import ngocthang.utils.SessionUtils;
import ngocthang.utils.UploadUtils;

@WebServlet(urlPatterns = {"/admin/profile", "/manager/profile", "/user/profile"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
    maxFileSize = 1024 * 1024 * 5,       // 5 MB
    maxRequestSize = 1024 * 1024 * 10    // 10 MB
)
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User currentUser = SessionUtils.getUser(request);
		
		if (currentUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		// Lấy thông tin user mới nhất từ database
		User user = userService.get(currentUser.getUserName());
		request.setAttribute("user", user);
		request.setAttribute("currentUser", currentUser);
		
		// Forward đến trang profile chung
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/profile.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User currentUser = SessionUtils.getUser(request);
		
		if (currentUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			
			// Lấy thông tin từ form
			String fullName = UploadUtils.getFieldValue(request, "fullName");
			String phone = UploadUtils.getFieldValue(request, "phone");
			
			// Validate và upload file avatar
			String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "webp", "jfif"};
			String validationError = UploadUtils.validateUpload(request, "avatar", 5, allowedExtensions);
			
			if (validationError != null) {
				request.setAttribute("error", validationError);
				request.setAttribute("currentUser", currentUser);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/views/profile.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			// Upload file avatar (nếu có)
			String avatarPath = UploadUtils.uploadFile(request, "avatar", "avatars");
			
			// Cập nhật thông tin user
			User userToUpdate = userService.get(currentUser.getUserName());
			if (userToUpdate != null) {
				boolean updated = false;
				
				if (fullName != null && !fullName.trim().isEmpty()) {
					userToUpdate.setFullName(fullName.trim());
					updated = true;
				}
				
				if (phone != null && !phone.trim().isEmpty()) {
					userToUpdate.setPhone(phone.trim());
					updated = true;
				}
				
				if (avatarPath != null && !avatarPath.trim().isEmpty()) {
					// Xóa avatar cũ nếu có
					if (userToUpdate.getAvatar() != null && !userToUpdate.getAvatar().isEmpty()) {
						UploadUtils.deleteFile(userToUpdate.getAvatar());
					}
					userToUpdate.setAvatar(avatarPath);
					updated = true;
				}
				
				if (updated) {
					// Cập nhật trong database
					boolean success = userService.updateProfile(userToUpdate);
					if (success) {
						// Cập nhật session
						request.getSession().setAttribute(Constant.SESSION_ACCOUNT, userToUpdate);
						request.setAttribute("success", "Cập nhật profile thành công!");
					} else {
						request.setAttribute("error", "Có lỗi xảy ra khi cập nhật profile!");
					}
				} else {
					request.setAttribute("error", "Không có thông tin nào được cập nhật!");
				}
			}
			
		} catch (IllegalStateException e) {
			// Xử lý lỗi file quá lớn từ Tomcat
			if (e.getMessage() != null && e.getMessage().contains("SizeLimitExceededException")) {
				request.setAttribute("error", "File không được quá 5MB. Vui lòng chọn file nhỏ hơn.");
			} else {
				request.setAttribute("error", "File quá lớn hoặc không hợp lệ. Vui lòng thử lại.");
			}
			request.setAttribute("currentUser", currentUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/profile.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
			request.setAttribute("currentUser", currentUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/views/profile.jsp");
			dispatcher.forward(request, response);
		}
		
		// Redirect về trang profile của role tương ứng
		String rolePath = getRolePath(currentUser.getRoleid());
		response.sendRedirect(request.getContextPath() + "/" + rolePath + "/profile");
	}
	
	private String getRolePath(int roleId) {
		switch (roleId) {
			case Constant.ROLE_ADMIN:
				return "admin";
			case Constant.ROLE_MANAGER:
				return "manager";
			case Constant.ROLE_USER:
				return "user";
			default:
				return "user";
		}
	}
}
