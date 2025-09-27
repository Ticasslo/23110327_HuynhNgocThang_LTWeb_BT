package ngocthang.dao;

import ngocthang.entity.User;

public interface IUserDAO_23110327 {
	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
    User findById(int id);
    User save(User user);
}


