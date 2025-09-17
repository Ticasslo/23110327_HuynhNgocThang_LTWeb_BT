package vn.ngocthang.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ngocthang.entity.User;
import vn.ngocthang.repository.UserRepository;
import vn.ngocthang.services.UserService;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.RandomUtils;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user != null && password != null && password.equals(user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        return userRepository.findByUserName(username).orElse(null);
    }

    @Override
    public boolean register(String email, String password, String username, String fullname, String phone) {
        if (userRepository.existsByUserName(username)) {
            return false;
        }
        
        // Tạo ID ngẫu nhiên cho user mới
        int userId = RandomUtils.generateUserId(100000, 999999);
        
        // Kiểm tra ID đã tồn tại chưa, nếu có thì tạo lại
        while (userRepository.existsById(userId)) {
            userId = RandomUtils.generateUserId(100000, 999999);
        }
        
        Timestamp created = new Timestamp(System.currentTimeMillis());
        User newUser = new User(userId, email, username, fullname, password, null, Constants.ROLE_USER, phone, created);
        userRepository.save(newUser);
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User findByUsernameOrEmail(String identifier) {
        return userRepository.findByUserNameOrEmail(identifier).orElse(null);
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        User user = get(username);
        if (user != null) {
            user.setPassWord(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean updateProfile(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
