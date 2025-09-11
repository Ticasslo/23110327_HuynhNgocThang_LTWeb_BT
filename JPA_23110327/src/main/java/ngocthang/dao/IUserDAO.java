package ngocthang.dao;

import ngocthang.entity.User;

public interface IUserDAO {
    User get(String username);
    
    //register
    void insert(User user);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    boolean checkExistId(int id);
    
    // Methods for forgot password
    User findByUserName(String username);
    User findByEmail(String email);
    boolean updatePassword(String username, String newPassword);
}
