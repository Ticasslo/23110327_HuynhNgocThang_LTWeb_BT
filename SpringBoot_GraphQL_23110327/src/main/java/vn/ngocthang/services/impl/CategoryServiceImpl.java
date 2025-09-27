package vn.ngocthang.services.impl;

// import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.ngocthang.entity.Category;
import vn.ngocthang.repository.CategoryRepository;
import vn.ngocthang.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        // Nếu là entity mới (id == 0)
        if (category.getId() == 0) {
            return categoryRepository.save(category);
        } else {
            // Nếu là update entity
            Optional<Category> opt = findById(category.getId());
            if (opt.isPresent()) {
                // Nếu tên danh mục trống, giữ lại tên cũ
                if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
                    category.setCategoryName(opt.get().getCategoryName());
                } else {
                    // Lấy lại images cũ nếu cần
                    if (category.getImages() == null || category.getImages().trim().isEmpty()) {
                        category.setImages(opt.get().getImages());
                    }
                }
            }
            return categoryRepository.save(category);
        }
    }

    @Override
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByCategoryNameContainingIgnoreCase(String categoryName) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
    }

    @Override
    public Page<Category> findByCategoryNameContainingIgnoreCase(String categoryName, Pageable pageable) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName, pageable);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }
}
