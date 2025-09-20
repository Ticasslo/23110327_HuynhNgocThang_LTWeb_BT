package vn.ngocthang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ngocthang.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Tìm kiếm theo nội dung tên
    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);
    
    // Tìm kiếm và Phân trang
    Page<Category> findByCategoryNameContainingIgnoreCase(String keyword, Pageable pageable);
}
