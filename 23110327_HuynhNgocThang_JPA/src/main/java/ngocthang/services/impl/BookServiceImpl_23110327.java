package ngocthang.services.impl;

import java.util.List;

import ngocthang.dao.IBookDAO_23110327;
import ngocthang.dao.impl.BookDAOImpl_23110327;
import ngocthang.entity.Book;
import ngocthang.services.IBookService_23110327;

public class BookServiceImpl_23110327 implements IBookService_23110327 {

    private final IBookDAO_23110327 bookDAO_23110327 = new BookDAOImpl_23110327();

    @Override
    public long countAll() {
        return bookDAO_23110327.countAll();
    }

    @Override
    public List<Book> findAllPaged(int page, int size) {
        return bookDAO_23110327.findAllPaged(page, size);
    }

    @Override
    public List<Object[]> findBooksWithAuthorsAndReviewCount(int page, int size) {
        return bookDAO_23110327.findBooksWithAuthorsAndReviewCount(page, size);
    }

    @Override
    public Book findById(int bookId) {
        return bookDAO_23110327.findById(bookId);
    }

    @Override
    public List<Object[]> findBookWithAuthorsAndReviews(int bookId) {
        return bookDAO_23110327.findBookWithAuthorsAndReviews(bookId);
    }

    @Override
    public Book save(Book book) {
        return bookDAO_23110327.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookDAO_23110327.update(book);
    }

    @Override
    public void deleteById(int id) {
        bookDAO_23110327.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return bookDAO_23110327.existsById(id);
    }

    @Override
    public boolean existsByIsbn(Long isbn) {
        return bookDAO_23110327.existsByIsbn(isbn);
    }

    @Override
    public boolean existsByIsbnExceptId(Long isbn, int excludeId) {
        return bookDAO_23110327.existsByIsbnExceptId(isbn, excludeId);
    }

    @Override
    public long countByTitleOrPublisherLike(String keyword) {
        return bookDAO_23110327.countByTitleOrPublisherLike(keyword);
    }

    @Override
    public List<Book> findByTitleOrPublisherLikePaged(String keyword, int page, int size) {
        return bookDAO_23110327.findByTitleOrPublisherLikePaged(keyword, page, size);
    }
}


