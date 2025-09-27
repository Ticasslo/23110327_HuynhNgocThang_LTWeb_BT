package ngocthang.services;

import ngocthang.entity.Author;
import java.util.List;

public interface IAuthorService_23110327 {
    Author findByAuthorName(String authorName);
    // CRUD
    Author findById(int id);
    Author save(Author author);
    Author update(Author author);
    void deleteById(int id);
    boolean existsById(int id);
    boolean existsByAuthorName(String authorName);
    boolean existsByAuthorNameExceptId(String authorName, int excludeId);
    // Pagination + search
    long countAll();
    long countByAuthorNameLike(String keyword);
    List<Author> findAllPaged(int page, int size);
    List<Author> findByAuthorNameLikePaged(String keyword, int page, int size);
}
