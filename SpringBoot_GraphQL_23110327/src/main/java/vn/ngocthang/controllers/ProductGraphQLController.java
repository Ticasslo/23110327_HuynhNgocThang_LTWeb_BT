package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import vn.ngocthang.entity.Product;
import vn.ngocthang.entity.Category;
import vn.ngocthang.entity.User;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * GraphQL Controller cho Product
 * Chỉ xử lý các operations liên quan đến Product
 */
@Controller
public class ProductGraphQLController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;

    /**
     * Query: Lấy tất cả products
     * GraphQL: products: [Product!]!
     */
    @QueryMapping
    public List<Product> products() {
        return productService.findAll();
    }

    /**
     * Query: Lấy product theo ID
     * GraphQL: product(id: ID!): Product
     */
    @QueryMapping
    public Product product(@Argument("id") Integer id) {
        Optional<Product> product = productService.findById(id);
        return product.orElse(null);
    }

    /**
     * Query: Lấy products theo category
     * GraphQL: productsByCategory(categoryId: ID!): [Product!]!
     */
    @QueryMapping
    public List<Product> productsByCategory(@Argument("categoryId") Integer categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    /**
     * Query: Lấy products theo user
     * GraphQL: productsByUser(userId: ID!): [Product!]!
     */
    @QueryMapping
    public List<Product> productsByUser(@Argument("userId") Integer userId) {
        return productService.findByUserId(userId);
    }

    /**
     * Query: Tìm kiếm products theo keyword
     * GraphQL: searchProducts(keyword: String!): [Product!]!
     */
    @QueryMapping
    public List<Product> searchProducts(@Argument("keyword") String keyword) {
        return productService.findByProductNameContainingIgnoreCase(keyword);
    }

    /**
     * Mutation: Tạo product mới
     * GraphQL: createProduct(input: ProductInput!): Product!
     */
    @MutationMapping
    public Product createProduct(@Argument("input") ProductInput input) {
        Product product = new Product();
        product.setProductName(input.getProductName());
        product.setDescription(input.getDescription());
        product.setImage(input.getImage());
        product.setPrice(BigDecimal.valueOf(input.getPrice()));
        product.setPurchases(input.getPurchases() != null ? input.getPurchases() : 0L);
        product.setStock(input.getStock() != null ? input.getStock() : 0);
        
        // Set Category
        Optional<Category> category = categoryService.findById(input.getCategoryId());
        category.ifPresent(product::setCategory);
        
        // Set User
        Optional<User> user = userService.findById(input.getUserId());
        user.ifPresent(product::setUser);
        
        return productService.save(product);
    }

    /**
     * Mutation: Cập nhật product
     * GraphQL: updateProduct(id: ID!, input: ProductInput!): Product!
     */
    @MutationMapping
    public Product updateProduct(@Argument("id") Integer id, @Argument("input") ProductInput input) {
        Optional<Product> existingProduct = productService.findById(id);
        
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setProductName(input.getProductName());
            product.setDescription(input.getDescription());
            product.setImage(input.getImage());
            product.setPrice(BigDecimal.valueOf(input.getPrice()));
            product.setPurchases(input.getPurchases() != null ? input.getPurchases() : product.getPurchases());
            product.setStock(input.getStock() != null ? input.getStock() : product.getStock());
            
            // Update Category
            Optional<Category> category = categoryService.findById(input.getCategoryId());
            category.ifPresent(product::setCategory);
            
            // Update User
            Optional<User> user = userService.findById(input.getUserId());
            user.ifPresent(product::setUser);
            
            return productService.save(product);
        }
        return null;
    }

    /**
     * Mutation: Xóa product
     * GraphQL: deleteProduct(id: ID!): Boolean!
     */
    @MutationMapping
    public Boolean deleteProduct(@Argument("id") Integer id) {
        try {
            productService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Input class cho ProductInput
     */
    public static class ProductInput {
        private String productName;
        private String description;
        private String image;
        private Double price;
        private Long purchases;
        private Integer stock;
        private Integer categoryId;
        private Integer userId;

        // Constructors
        public ProductInput() {}

        // Getters and Setters
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        public Long getPurchases() { return purchases; }
        public void setPurchases(Long purchases) { this.purchases = purchases; }

        public Integer getStock() { return stock; }
        public void setStock(Integer stock) { this.stock = stock; }

        public Integer getCategoryId() { return categoryId; }
        public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

        public Integer getUserId() { return userId; }
        public void setUserId(Integer userId) { this.userId = userId; }
    }
}
