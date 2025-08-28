package ngocthang.dao;

import ngocthang.models.User;

public interface UserDao {
    User get(String username);
}
