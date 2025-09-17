package ngocthang.services;

import ngocthang.entity.User;

public interface IUserService {
	// Đăng nhập: trả về User nếu đúng, null nếu sai
	User login(String username, String password);

	// Lấy thông tin user theo username
	User get(String username);

	// Dang ky register
	void insert(User user);

	boolean register(String email, String password, String username, String fullname, String phone);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean checkExistPhone(String phone);

	User findByUsernameOrEmail(String identifier);

	boolean updatePassword(String username, String newPassword);
	
	// Cập nhật profile user
	boolean updateProfile(User user);
}
