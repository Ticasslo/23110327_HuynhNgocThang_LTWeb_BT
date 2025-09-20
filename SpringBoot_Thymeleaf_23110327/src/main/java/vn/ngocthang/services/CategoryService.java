package vn.ngocthang.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.ngocthang.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findById(Integer id);
    Category save(Category category);
    void deleteById(Integer id);
    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
    Page<Category> findByCategoryNameContainingIgnoreCase(String categoryName, Pageable pageable);
    long count();
}
