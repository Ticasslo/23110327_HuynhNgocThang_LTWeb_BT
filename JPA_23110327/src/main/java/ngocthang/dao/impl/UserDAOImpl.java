package ngocthang.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ngocthang.configs.JPAConfig;
import ngocthang.dao.IUserDAO;
import ngocthang.entity.User;

public class UserDAOImpl implements IUserDAO {

    @Override
    public User get(String username) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT u FROM User u WHERE u.userName = :username";
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("username", username);
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public void insert(User user) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            EntityTransaction trans = enma.getTransaction();
            try {
                trans.begin();
                enma.persist(user);
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean checkExistEmail(String email) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return count > 0;
        }
    }

    @Override
    public boolean checkExistUsername(String username) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.userName = :username";
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            query.setParameter("username", username);
            Long count = query.getSingleResult();
            return count > 0;
        }
    }

    @Override
    public boolean checkExistPhone(String phone) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.phone = :phone";
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            query.setParameter("phone", phone);
            Long count = query.getSingleResult();
            return count > 0;
        }
    }

    @Override
    public boolean checkExistId(int id) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.id = :id";
            TypedQuery<Long> query = enma.createQuery(jpql, Long.class);
            query.setParameter("id", id);
            Long count = query.getSingleResult();
            return count > 0;
        }
    }

    @Override
    public User findByUserName(String username) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT u FROM User u WHERE u.userName = :username";
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("username", username);
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("email", email);
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            EntityTransaction trans = enma.getTransaction();
            try {
                trans.begin();
                String jpql = "UPDATE User u SET u.passWord = :newPassword WHERE u.userName = :username";
                jakarta.persistence.Query query = enma.createQuery(jpql);
                query.setParameter("newPassword", newPassword);
                query.setParameter("username", username);
                int result = query.executeUpdate();
                trans.commit();
                return result > 0;
            } catch (Exception e) {
                trans.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }
}
