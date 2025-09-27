package ngocthang.services;

import java.util.List;
import ngocthang.entity.Book;

public interface IBookService_23110327 {
    long countAll();
    List<Book> findAllPaged(int page, int size);
    List<Object[]> findBooksWithAuthorsAndReviewCount(int page, int size);
    Book findById(int bookId);
    List<Object[]> findBookWithAuthorsAndReviews(int bookId);
    
    // CRUD
    Book save(Book book);
    Book update(Book book);
    void deleteById(int id);
    boolean existsById(int id);
    boolean existsByIsbn(Long isbn);
    boolean existsByIsbnExceptId(Long isbn, int excludeId);
    // Pagination + search
    long countByTitleOrPublisherLike(String keyword);
    List<Book> findByTitleOrPublisherLikePaged(String keyword, int page, int size);
}


