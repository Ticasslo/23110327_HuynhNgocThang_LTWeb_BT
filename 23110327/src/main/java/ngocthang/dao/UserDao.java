package ngocthang.dao;

import ngocthang.models.User;

public interface UserDao {
    User get(String username);
    
    //register
    void insert(User user);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    
    // Methods for forgot password
    User findByUserName(String username);
    User findByEmail(String email);
    boolean updatePassword(String username, String newPassword);
}
