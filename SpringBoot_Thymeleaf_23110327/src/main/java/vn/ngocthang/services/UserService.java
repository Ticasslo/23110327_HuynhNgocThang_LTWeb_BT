package vn.ngocthang.services;

import vn.ngocthang.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User login(String username, String password);
    User get(String username);
    User get(int id);
    User create(User user);
    User update(User user);
    void delete(int id);
    
    // Methods for AdminUserController
    Optional<User> findById(Integer id);
    User save(User user);
    User saveAndFlush(User user);
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    List<User> findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(String fullName, String userName);
    
    // Methods for RegisterController
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean register(String email, String password, String username, String fullname, String phone);
}
