package ngocthang.dao.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import ngocthang.configs.JPAConfig_23110327;
import ngocthang.dao.IBookDAO_23110327;
import ngocthang.entity.Book;

public class BookDAOImpl_23110327 implements IBookDAO_23110327 {

    @Override
    public long countAll() {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.createQuery("SELECT COUNT(b) FROM Book b", Long.class).getSingleResult();
        }
    }

    @Override
    public List<Book> findAllPaged(int page, int size) {
        int first = (page - 1) * size;
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            TypedQuery<Book> q = em.createQuery("SELECT b FROM Book b ORDER BY b.bookId", Book.class);
            q.setFirstResult(first);
            q.setMaxResults(size);
            return q.getResultList();
        }
    }

    @Override
    public List<Object[]> findBooksWithAuthorsAndReviewCount(int page, int size) {
        int first = (page - 1) * size;
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            String sql = """
                SELECT b.bookid, b.isbn, b.title, b.publisher, b.price, 
                       b.publish_date, b.cover_image, b.quantity,
                       STRING_AGG(a.author_name, ', ') as authors,
                       COUNT(r.bookid) as reviewCount
                FROM books b
                LEFT JOIN book_author ba ON b.bookid = ba.bookid
                LEFT JOIN author a ON ba.author_id = a.author_id
                LEFT JOIN rating r ON b.bookid = r.bookid
                GROUP BY b.bookid, b.isbn, b.title, b.publisher, b.price, 
                         b.publish_date, b.cover_image, b.quantity
                ORDER BY b.bookid
                OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                """;
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, first);
            q.setParameter(2, size);
            return q.getResultList();
        }
    }

    @Override
    public Book findById(int bookId) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.find(Book.class, bookId);
        }
    }

    @Override
    public List<Object[]> findBookWithAuthorsAndReviews(int bookId) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            // Query riêng để lấy thông tin sách + tác giả
            String bookSql = """
                SELECT b.bookid, b.isbn, b.title, b.publisher, b.price, 
                       b.publish_date, b.cover_image, b.quantity,
                       STRING_AGG(a.author_name, ', ') as authors
                FROM books b
                LEFT JOIN book_author ba ON b.bookid = ba.bookid
                LEFT JOIN author a ON ba.author_id = a.author_id
                WHERE b.bookid = ?
                GROUP BY b.bookid, b.isbn, b.title, b.publisher, b.price, 
                         b.publish_date, b.cover_image, b.quantity
                """;
            Query bookQuery = em.createNativeQuery(bookSql);
            bookQuery.setParameter(1, bookId);
            List<Object[]> bookData = bookQuery.getResultList();
            
            // Query riêng để lấy reviews với user info
            String reviewSql = """
                SELECT u.fullname, r.rating, r.review_text
                FROM rating r
                LEFT JOIN users u ON r.userid = u.id
                WHERE r.bookid = ?
                ORDER BY r.userid
                """;
            Query reviewQuery = em.createNativeQuery(reviewSql);
            reviewQuery.setParameter(1, bookId);
            List<Object[]> reviewData = reviewQuery.getResultList();
            
            // Kết hợp dữ liệu
            List<Object[]> result = new ArrayList<>();
            if (!bookData.isEmpty()) {
                Object[] book = bookData.get(0);
                for (Object[] review : reviewData) {
                    Object[] combined = new Object[book.length + review.length];
                    System.arraycopy(book, 0, combined, 0, book.length);
                    System.arraycopy(review, 0, combined, book.length, review.length);
                    result.add(combined);
                }
                // Nếu không có review, vẫn trả về thông tin sách
                if (reviewData.isEmpty()) {
                    result.add(book);
                }
            }
            
            return result;
        }
    }

    @Override
    public Book save(Book book) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        }
    }

    @Override
    public Book update(Book book) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            Book merged = em.merge(book);
            em.getTransaction().commit();
            return merged;
        }
    }

    @Override
    public void deleteById(int id) {
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            em.getTransaction().begin();
            Book book = em.find(Book.class, id);
            if (book != null) {
                em.remove(book);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    @Override
    public boolean existsByIsbn(Long isbn) {
        if (isbn == null) return false;
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(b) FROM Book b WHERE b.isbn = :isbn", Long.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            return count != null && count > 0;
        }
    }

    @Override
    public boolean existsByIsbnExceptId(Long isbn, int excludeId) {
        if (isbn == null) return false;
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(b) FROM Book b WHERE b.isbn = :isbn AND b.bookId <> :id", Long.class)
                    .setParameter("isbn", isbn)
                    .setParameter("id", excludeId)
                    .getSingleResult();
            return count != null && count > 0;
        }
    }

    @Override
    public long countByTitleOrPublisherLike(String keyword) {
        String kw = "%" + keyword.trim().toLowerCase() + "%";
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.createQuery(
                    "SELECT COUNT(b) FROM Book b WHERE LOWER(b.title) LIKE :kw OR LOWER(b.publisher) LIKE :kw",
                    Long.class)
                    .setParameter("kw", kw)
                    .getSingleResult();
        }
    }

    @Override
    public List<Book> findByTitleOrPublisherLikePaged(String keyword, int page, int size) {
        int first = (page - 1) * size;
        String kw = "%" + keyword.trim().toLowerCase() + "%";
        try (EntityManager em = JPAConfig_23110327.getEntityManager()) {
            return em.createQuery(
                    "SELECT b FROM Book b WHERE LOWER(b.title) LIKE :kw OR LOWER(b.publisher) LIKE :kw ORDER BY b.bookId",
                    Book.class)
                    .setParameter("kw", kw)
                    .setFirstResult(first)
                    .setMaxResults(size)
                    .getResultList();
        }
    }
}


