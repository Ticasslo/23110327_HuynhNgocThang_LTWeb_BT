package ngocthang.services;

import ngocthang.entity.User;

public interface IUserService_23110327 {
	User login(String usernameOrEmail, String password);
	User getByUsernameOrEmail(String usernameOrEmail);
    User findById(Integer id);
    User save(User user);
}


