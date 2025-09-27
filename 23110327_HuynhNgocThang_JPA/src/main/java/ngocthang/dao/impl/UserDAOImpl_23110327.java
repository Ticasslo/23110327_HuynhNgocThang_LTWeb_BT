package ngocthang.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import ngocthang.configs.JPAConfig_23110327;
import ngocthang.dao.IUserDAO_23110327;
import ngocthang.entity.User;
import java.util.List;

public class UserDAOImpl_23110327 implements IUserDAO_23110327 {

	@Override
	public User findByEmail(String email) {
		try (EntityManager enma = JPAConfig_23110327.getEntityManager()) {
			TypedQuery<User> query = enma.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		try (EntityManager enma = JPAConfig_23110327.getEntityManager()) {
			TypedQuery<User> query = enma.createQuery(
					"SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findById(int id) {
		try (EntityManager enma = JPAConfig_23110327.getEntityManager()) {
			return enma.find(User.class, id);
		}
	}

	@Override
	public User save(User user) {
		try (EntityManager enma = JPAConfig_23110327.getEntityManager()) {
			enma.getTransaction().begin();
			User result;
			if (user.getId() == 0) {
				// Entity mới - dùng persist
				enma.persist(user);
				result = user;
			} else {
				// Entity đã tồn tại - dùng merge
				result = enma.merge(user);
			}
			enma.getTransaction().commit();
			return result;
		}
	}
}


