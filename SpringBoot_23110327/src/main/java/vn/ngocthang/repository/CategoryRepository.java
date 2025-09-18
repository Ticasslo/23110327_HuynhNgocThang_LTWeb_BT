package vn.ngocthang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ngocthang.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    java.util.List<Category> findByCategoryNameContainingIgnoreCase(String keyword);
}
