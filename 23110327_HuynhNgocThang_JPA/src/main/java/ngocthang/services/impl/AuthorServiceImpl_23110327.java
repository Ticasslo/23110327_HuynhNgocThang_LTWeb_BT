package ngocthang.services.impl;

import ngocthang.dao.IAuthorDAO_23110327;
import ngocthang.dao.impl.AuthorDAOImpl_23110327;
import ngocthang.entity.Author;
import ngocthang.services.IAuthorService_23110327;
import java.util.List;

public class AuthorServiceImpl_23110327 implements IAuthorService_23110327 {

    private final IAuthorDAO_23110327 authorDAO_23110327 = new AuthorDAOImpl_23110327();

    @Override
    public Author findByAuthorName(String authorName) {
        return authorDAO_23110327.findByAuthorName(authorName);
    }

    @Override
    public Author findById(int id) {
        return authorDAO_23110327.findById(id);
    }

    @Override
    public Author save(Author author) {
        return authorDAO_23110327.save(author);
    }

    @Override
    public Author update(Author author) {
        return authorDAO_23110327.update(author);
    }

    @Override
    public void deleteById(int id) {
        authorDAO_23110327.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return authorDAO_23110327.existsById(id);
    }

    @Override
    public boolean existsByAuthorName(String authorName) {
        return authorDAO_23110327.existsByAuthorName(authorName);
    }

    @Override
    public boolean existsByAuthorNameExceptId(String authorName, int excludeId) {
        return authorDAO_23110327.existsByAuthorNameExceptId(authorName, excludeId);
    }

    @Override
    public long countAll() {
        return authorDAO_23110327.countAll();
    }

    @Override
    public long countByAuthorNameLike(String keyword) {
        return authorDAO_23110327.countByAuthorNameLike(keyword);
    }

    @Override
    public List<Author> findAllPaged(int page, int size) {
        return authorDAO_23110327.findAllPaged(page, size);
    }

    @Override
    public List<Author> findByAuthorNameLikePaged(String keyword, int page, int size) {
        return authorDAO_23110327.findByAuthorNameLikePaged(keyword, page, size);
    }
}
