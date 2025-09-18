package vn.ngocthang.services;

import vn.ngocthang.entity.User;

public interface UserService {
    User login(String username, String password);
    User get(String username);
    User get(int id);
    User create(User user);
    User update(User user);
    void delete(int id);
    
    // Methods for RegisterController
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean register(String email, String password, String username, String fullname, String phone);
}