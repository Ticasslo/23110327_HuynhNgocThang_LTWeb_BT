package ngocthang.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ngocthang.configs.JPAConfig;
import ngocthang.dao.ICategoryDAO;
import ngocthang.entity.Category;

public class CategoryDAOImpl implements ICategoryDAO {
    
    @Override
    public void insert(Category category) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            EntityTransaction trans = enma.getTransaction();
            try {
                trans.begin();
                enma.persist(category);
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
                throw e;
            }
        }
    }
    
    @Override
    public void edit(Category category) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            EntityTransaction trans = enma.getTransaction();
            try {
                trans.begin();
                enma.merge(category);
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
                throw e;
            }
        }
    }
    
    @Override
    public void delete(int id) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            EntityTransaction trans = enma.getTransaction();
            try {
                trans.begin();
                Category category = enma.find(Category.class, id);
                if (category != null) {
                    enma.remove(category);
                }
                trans.commit();
            } catch (Exception e) {
                trans.rollback();
                throw e;
            }
        }
    }
    
    @Override
    public Category get(int id) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            return enma.find(Category.class, id);
        }
    }
    
    @Override
    public Category get(String name) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT c FROM Category c WHERE c.catename = :name";
            TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
            query.setParameter("name", name);
            List<Category> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }
    
    @Override
    public List<Category> getAll() {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            TypedQuery<Category> query = enma.createQuery("SELECT c FROM Category c", Category.class);
            return query.getResultList();
        }
    }
    
    @Override
    public List<Category> getCategoriesByUserId(int userid) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT c FROM Category c WHERE c.userid = :userid";
            TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
            query.setParameter("userid", userid);
            return query.getResultList();
        }
    }
    
    @Override
    public List<Category> search(String keyword) {
        try (EntityManager enma = JPAConfig.getEntityManager()) {
            String jpql = "SELECT c FROM Category c WHERE c.catename LIKE :keyword";
            TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        }
    }
}