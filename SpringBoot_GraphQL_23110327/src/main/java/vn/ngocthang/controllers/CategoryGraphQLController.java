package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import vn.ngocthang.entity.Category;
import vn.ngocthang.services.CategoryService;

import java.util.List;
import java.util.Optional;

/**
 * GraphQL Controller cho Category
 * Chỉ xử lý các operations liên quan đến Category
 */
@Controller
public class CategoryGraphQLController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Query: Lấy tất cả categories
     * GraphQL: categories: [Category!]!
     */
    @QueryMapping
    public List<Category> categories() {
        return categoryService.findAll();
    }

    /**
     * Query: Lấy category theo ID
     * GraphQL: category(id: ID!): Category
     */
    @QueryMapping
    public Category category(@Argument("id") Integer id) {
        Optional<Category> category = categoryService.findById(id);
        return category.orElse(null);
    }

    /**
     * Mutation: Tạo category mới
     * GraphQL: createCategory(input: CategoryInput!): Category!
     */
    @MutationMapping
    public Category createCategory(@Argument("input") CategoryInput input) {
        Category category = new Category();
        category.setCategoryName(input.getCategoryName());
        category.setImages(input.getImages());
        
        return categoryService.save(category);
    }

    /**
     * Mutation: Cập nhật category
     * GraphQL: updateCategory(id: ID!, input: CategoryInput!): Category!
     */
    @MutationMapping
    public Category updateCategory(@Argument("id") Integer id, @Argument("input") CategoryInput input) {
        Optional<Category> existingCategory = categoryService.findById(id);
        
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setCategoryName(input.getCategoryName());
            category.setImages(input.getImages());
            
            return categoryService.save(category);
        }
        return null;
    }

    /**
     * Mutation: Xóa category
     * GraphQL: deleteCategory(id: ID!): Boolean!
     */
    @MutationMapping
    public Boolean deleteCategory(@Argument("id") Integer id) {
        try {
            categoryService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Input class cho CategoryInput
     */
    public static class CategoryInput {
        private String categoryName;
        private String images;

        // Constructors
        public CategoryInput() {}

        // Getters and Setters
        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

        public String getImages() { return images; }
        public void setImages(String images) { this.images = images; }
    }
}
