package ngocthang.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import ngocthang.configs.JPAConfig_23110327;
import ngocthang.dao.IAuthorDAO_23110327;
import ngocthang.entity.Author;
import java.util.List;

public class AuthorDAOImpl_23110327 implements IAuthorDAO_23110327 {

    @Override
    public Author findByAuthorName(String authorName) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.authorName = :authorName", Author.class);
            query.setParameter("authorName", authorName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Author findById(int id) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.find(Author.class, id);
        }
    }

    @Override
    public Author save(Author author) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();
            return author;
        }
    }

    @Override
    public Author update(Author author) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            Author merged = em.merge(author);
            em.getTransaction().commit();
            return merged;
        }
    }

    @Override
    public void deleteById(int id) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            Author author = em.find(Author.class, id);
            if (author != null) {
                em.remove(author);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    @Override
    public boolean existsByAuthorName(String authorName) {
        if (authorName == null || authorName.trim().isEmpty()) return false;
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(a) FROM Author a WHERE a.authorName = :authorName", Long.class)
                    .setParameter("authorName", authorName.trim())
                    .getSingleResult();
            return count != null && count > 0;
        }
    }

    @Override
    public boolean existsByAuthorNameExceptId(String authorName, int excludeId) {
        if (authorName == null || authorName.trim().isEmpty()) return false;
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(a) FROM Author a WHERE a.authorName = :authorName AND a.authorId <> :id", Long.class)
                    .setParameter("authorName", authorName.trim())
                    .setParameter("id", excludeId)
                    .getSingleResult();
            return count != null && count > 0;
        }
    }

    @Override
    public long countAll() {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.createQuery("SELECT COUNT(a) FROM Author a", Long.class).getSingleResult();
        }
    }

    @Override
    public long countByAuthorNameLike(String keyword) {
        String kw = "%" + keyword.trim().toLowerCase() + "%";
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.createQuery(
                    "SELECT COUNT(a) FROM Author a WHERE LOWER(a.authorName) LIKE :kw",
                    Long.class)
                    .setParameter("kw", kw)
                    .getSingleResult();
        }
    }

    @Override
    public List<Author> findAllPaged(int page, int size) {
        int first = (page - 1) * size;
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.createQuery("SELECT a FROM Author a ORDER BY a.authorId", Author.class)
                    .setFirstResult(first)
                    .setMaxResults(size)
                    .getResultList();
        }
    }

    @Override
    public List<Author> findByAuthorNameLikePaged(String keyword, int page, int size) {
        int first = (page - 1) * size;
        String kw = "%" + keyword.trim().toLowerCase() + "%";
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.createQuery(
                    "SELECT a FROM Author a WHERE LOWER(a.authorName) LIKE :kw ORDER BY a.authorId",
                    Author.class)
                    .setParameter("kw", kw)
                    .setFirstResult(first)
                    .setMaxResults(size)
                    .getResultList();
        }
    }
}
