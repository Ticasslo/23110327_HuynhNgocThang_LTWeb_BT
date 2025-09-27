package ngocthang.services.impl;

import ngocthang.dao.IUserDAO_23110327;
import ngocthang.dao.impl.UserDAOImpl_23110327;
import ngocthang.entity.User;
import ngocthang.services.IUserService_23110327;

public class UserServiceImpl_23110327 implements IUserService_23110327 {

    private final IUserDAO_23110327 userDAO_23110327 = new UserDAOImpl_23110327();

    @Override
    public User login(String usernameOrEmail, String password) {
        return userDAO_23110327.findByEmailAndPassword(usernameOrEmail, password);
    }

    @Override
    public User getByUsernameOrEmail(String usernameOrEmail) {
        return userDAO_23110327.findByEmail(usernameOrEmail);
    }

    @Override
    public User findById(Integer id) {
        return userDAO_23110327.findById(id);
    }

    @Override
    public User save(User user) {
        return userDAO_23110327.save(user);
    }
}