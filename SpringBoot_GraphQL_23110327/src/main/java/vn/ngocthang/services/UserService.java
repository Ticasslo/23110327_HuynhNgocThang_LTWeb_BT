package vn.ngocthang.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.ngocthang.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
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
    Page<User> findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(String fullName, String userName, Pageable pageable);
    
    // Methods for RegisterController
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean register(String email, String password, String username, String fullname, String phone);
}
