package vn.ngocthang.services.impl;

// import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.ngocthang.entity.User;
import vn.ngocthang.repository.UserRepository;
import vn.ngocthang.services.UserService;

import java.sql.Timestamp;
import vn.ngocthang.utils.RandomUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        
        User user = this.get(username);
        if (user != null) {
            System.out.println("User found: " + user.getUserName());
            System.out.println("User password: " + user.getPassWord());
            System.out.println("Password match: " + password.equals(user.getPassWord()));
            
            if (password.equals(user.getPassWord())) {
                System.out.println("Login SUCCESS");
                return user;
            }
        } else {
            System.out.println("User NOT found");
        }
        System.out.println("Login FAILED");
        return null;
    }

    @Override
    public User get(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElse(null);
    }

    @Override
    public User get(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<User> findAll() {
        // Gọi hàm findAll() trong repository
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    // Methods for AdminUserController
    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        // Nếu là entity mới (id == 0)
        if (user.getId() == 0) {
            // Set createdDate cho user mới
            if (user.getCreatedDate() == null) {
                user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            }
            return userRepository.save(user);
        } else {
            // Nếu là update entity
            Optional<User> opt = findById(user.getId());
            if (opt.isPresent()) {
                // Nếu username trống, giữ lại username cũ
                if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
                    user.setUserName(opt.get().getUserName());
                }
                // Nếu fullName trống, giữ lại fullName cũ
                if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
                    user.setFullName(opt.get().getFullName());
                }
                // Nếu email trống, giữ lại email cũ
                if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                    user.setEmail(opt.get().getEmail());
                }
                // Nếu password trống, giữ lại password cũ
                if (user.getPassWord() == null || user.getPassWord().trim().isEmpty()) {
                    user.setPassWord(opt.get().getPassWord());
                }
                // Nếu phone trống, giữ lại phone cũ
                if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
                    user.setPhone(opt.get().getPhone());
                }
                // Nếu avatar trống, giữ lại avatar cũ
                if (user.getAvatar() == null || user.getAvatar().trim().isEmpty()) {
                    user.setAvatar(opt.get().getAvatar());
                }
                // Nếu roleid không hợp lệ, giữ lại roleid cũ
                if (user.getRoleid() != 1 && user.getRoleid() != 2) {
                    user.setRoleid(opt.get().getRoleid());
                }
                // Nếu createdDate null, giữ lại createdDate cũ
                if (user.getCreatedDate() == null) {
                    user.setCreatedDate(opt.get().getCreatedDate());
                }
            }
            return userRepository.save(user);
        }
    }

    @Override
    public User saveAndFlush(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public List<User> findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(String fullName, String userName) {
        return userRepository.findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(fullName, userName);
    }

    @Override
    public Page<User> findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(String fullName, String userName, Pageable pageable) {
        return userRepository.findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(fullName, userName, pageable);
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
    public boolean register(String email, String password, String username, String fullname, String phone) {
        try {
            // Tạo ID ngẫu nhiên cho user mới
            int newId = generateNewUserId();
            
            User newUser = new User();
            newUser.setId(newId);
            newUser.setEmail(email);
            newUser.setUserName(username);
            newUser.setPassWord(password);
            newUser.setFullName(fullname);
            newUser.setPhone(phone);
            newUser.setRoleid(2); // Default role là User (2)
            newUser.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis())); // Set createdDate
            
            userRepository.save(newUser);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
    
    private int generateNewUserId() {
        // Tạo ID ngẫu nhiên từ 100000 đến 999999
        return RandomUtils.getRandomId();
    }
}
