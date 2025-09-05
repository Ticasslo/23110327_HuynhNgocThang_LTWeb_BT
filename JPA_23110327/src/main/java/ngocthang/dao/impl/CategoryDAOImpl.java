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
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(category);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }
    
    @Override
    public void update(Category category) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(category);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }
    
    @Override
    public void delete(int id) throws Exception {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Category category = enma.find(Category.class, id);
            if (category != null) {
                enma.remove(category);
            } else {
                throw new Exception("Không tìm thấy danh mục");
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }
    
    @Override
    public Category findById(int id) {
        EntityManager enma = JPAConfig.getEntityManager();
        Category category = enma.find(Category.class, id);
        enma.close();
        return category;
    }
    
    @Override
    public List<Category> findAll() {
        EntityManager enma = JPAConfig.getEntityManager();
        TypedQuery<Category> query = enma.createQuery("SELECT c FROM Category c", Category.class);
        List<Category> list = query.getResultList();
        enma.close();
        return list;
    }
    
    @Override
    public List<Category> findByName(String categoryname) {
        EntityManager enma = JPAConfig.getEntityManager();
        String jpql = "SELECT c FROM Category c WHERE c.categoryname LIKE :categoryname";
        TypedQuery<Category> query = enma.createQuery(jpql, Category.class);
        query.setParameter("categoryname", "%" + categoryname + "%");
        List<Category> list = query.getResultList();
        enma.close();
        return list;
    }
}