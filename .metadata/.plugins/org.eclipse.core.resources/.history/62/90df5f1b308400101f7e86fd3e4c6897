package ngocthang.service.impl;

import ngocthang.dao.impl.UserDaoImpl;
import ngocthang.models.User;
import ngocthang.service.UserService;

public class UserServiceImpl implements UserService {
    UserDaoImpl userDao = new UserDaoImpl();

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
        if (username == null || username.isEmpty()) return null;
        return userDao.get(username);
    }
}
