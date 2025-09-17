package vn.ngocthang.services;

import vn.ngocthang.entity.User;

public interface UserService {
    // Đăng nhập: trả về User nếu đúng, null nếu sai
    User login(String username, String password);

    // Lấy thông tin user theo username
    User get(String username);

    // Đăng ký register
    boolean register(String email, String password, String username, String fullname, String phone);

    // Kiểm tra email đã tồn tại chưa
    boolean checkExistEmail(String email);

    // Kiểm tra username đã tồn tại chưa
    boolean checkExistUsername(String username);

    // Kiểm tra phone đã tồn tại chưa
    boolean checkExistPhone(String phone);

    // Tìm user theo username hoặc email
    User findByUsernameOrEmail(String identifier);

    // Cập nhật mật khẩu
    boolean updatePassword(String username, String newPassword);
    
    // Cập nhật profile user
    boolean updateProfile(User user);
}
