package ngocthang.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ngocthang.configs.JPAConfig_23110327;
import ngocthang.dao.IRatingDAO_23110327;
import ngocthang.entity.Rating;
import java.util.List;

public class RatingDAOImpl_23110327 implements IRatingDAO_23110327 {

    @Override
    public Rating save(Rating rating) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            Rating result;
            if (rating.getId() == null) {
                // Rating mới - dùng persist
                em.persist(rating);
                result = rating;
            } else {
                // Rating đã tồn tại - dùng merge
                result = em.merge(rating);
            }
            em.getTransaction().commit();
            return result;
        }
    }

    @Override
    public Rating update(Rating rating) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            Rating merged = em.merge(rating);
            em.getTransaction().commit();
            return merged;
        }
    }

    @Override
    public void deleteByUserIdAndBookId(int userId, int bookId) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            Rating rating = em.find(Rating.class, new Rating.RatingId(userId, bookId));
            if (rating != null) {
                em.remove(rating);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public Rating findByUserIdAndBookId(int userId, int bookId) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.find(Rating.class, new Rating.RatingId(userId, bookId));
        }
    }

    @Override
    public List<Rating> findByBookId(int bookId) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            TypedQuery<Rating> query = em.createQuery(
                "SELECT r FROM Rating r WHERE r.bookId = :bookId ORDER BY r.userId", 
                Rating.class);
            query.setParameter("bookId", bookId);
            return query.getResultList();
        }
    }

    @Override
    public boolean existsByUserIdAndBookId(int userId, int bookId) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            Long count = em.createQuery(
                "SELECT COUNT(r) FROM Rating r WHERE r.userId = :userId AND r.bookId = :bookId", 
                Long.class)
                .setParameter("userId", userId)
                .setParameter("bookId", bookId)
                .getSingleResult();
            return count != null && count > 0;
        }
    }
}
