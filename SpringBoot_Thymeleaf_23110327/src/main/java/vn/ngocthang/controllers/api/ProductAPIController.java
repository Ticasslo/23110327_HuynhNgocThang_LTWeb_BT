package vn.ngocthang.controllers.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import vn.ngocthang.entity.Category;
import vn.ngocthang.entity.Product;
import vn.ngocthang.entity.User;
import vn.ngocthang.model.Response;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.services.StorageService;
import vn.ngocthang.services.UserService;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product API", description = "API quản lý sản phẩm")
public class ProductAPIController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StorageService storageService;
    
    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả sản phẩm")
    public ResponseEntity<Response> getAllProducts() {
        try {
            List<Product> products = productService.findAll();
            return ResponseEntity.ok(Response.success("Lấy danh sách sản phẩm thành công", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Lấy sản phẩm theo ID")
    public ResponseEntity<Response> getProductById(
            @Parameter(description = "ID của sản phẩm") @PathVariable("id") Integer id) {
        try {
            Optional<Product> product = productService.findById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(Response.success("Lấy sản phẩm thành công", product.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error("Không tìm thấy sản phẩm với ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy sản phẩm: " + e.getMessage()));
        }
    }
    
    @PostMapping
    @Operation(summary = "Thêm sản phẩm mới")
    public ResponseEntity<Response> addProduct(
            @Parameter(description = "Tên sản phẩm") @RequestParam("productName") String productName,
            @Parameter(description = "Mô tả sản phẩm") @RequestParam(value = "description", required = false) String description,
            @Parameter(description = "Giá sản phẩm") @RequestParam("price") BigDecimal price,
            @Parameter(description = "Số lượng tồn kho") @RequestParam("stock") Integer stock,
            @Parameter(description = "ID danh mục") @RequestParam("categoryId") Integer categoryId,
            @Parameter(description = "ID người dùng") @RequestParam("userId") Integer userId,
            @Parameter(description = "Hình ảnh sản phẩm") @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Kiểm tra danh mục tồn tại
            Optional<Category> category = categoryService.findById(categoryId);
            if (category.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Response.error("Không tìm thấy danh mục với ID: " + categoryId));
            }
            
            // Kiểm tra người dùng tồn tại
            Optional<User> user = userService.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Response.error("Không tìm thấy người dùng với ID: " + userId));
            }
            
            Product product = new Product();
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setPurchases(0L);
            product.setCategory(category.get());
            product.setUser(user.get());
            
            // Xử lý upload hình ảnh
            if (image != null && !image.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String fileName = storageService.getStorageFilename(image, uuid.toString());
                product.setImage(fileName);
                storageService.store(image, fileName);
            }
            
            Product savedProduct = productService.save(product);
            return ResponseEntity.ok(Response.success("Thêm sản phẩm thành công", savedProduct));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi thêm sản phẩm: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật sản phẩm")
    public ResponseEntity<Response> updateProduct(
            @Parameter(description = "ID của sản phẩm") @PathVariable("id") Integer id,
            @Parameter(description = "Tên sản phẩm") @RequestParam("productName") String productName,
            @Parameter(description = "Mô tả sản phẩm") @RequestParam(value = "description", required = false) String description,
            @Parameter(description = "Giá sản phẩm") @RequestParam("price") BigDecimal price,
            @Parameter(description = "Số lượng tồn kho") @RequestParam("stock") Integer stock,
            @Parameter(description = "ID danh mục") @RequestParam("categoryId") Integer categoryId,
            @Parameter(description = "ID người dùng") @RequestParam("userId") Integer userId,
            @Parameter(description = "Hình ảnh sản phẩm") @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Optional<Product> optProduct = productService.findById(id);
            if (optProduct.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error("Không tìm thấy sản phẩm với ID: " + id));
            }
            
            // Kiểm tra danh mục tồn tại
            Optional<Category> category = categoryService.findById(categoryId);
            if (category.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Response.error("Không tìm thấy danh mục với ID: " + categoryId));
            }
            
            // Kiểm tra người dùng tồn tại
            Optional<User> user = userService.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Response.error("Không tìm thấy người dùng với ID: " + userId));
            }
            
            Product product = optProduct.get();
            product.setProductName(productName);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(category.get());
            product.setUser(user.get());
            
            // Xử lý upload hình ảnh mới
            if (image != null && !image.isEmpty()) {
                // Xóa hình ảnh cũ nếu có
                if (product.getImage() != null && !product.getImage().isEmpty()) {
                    try {
                        storageService.delete(product.getImage());
                    } catch (Exception e) {
                        // Log lỗi nhưng không dừng quá trình
                        System.err.println("Không thể xóa hình ảnh cũ: " + e.getMessage());
                    }
                }
                
                UUID uuid = UUID.randomUUID();
                String fileName = storageService.getStorageFilename(image, uuid.toString());
                product.setImage(fileName);
                storageService.store(image, fileName);
            }
            
            Product updatedProduct = productService.save(product);
            return ResponseEntity.ok(Response.success("Cập nhật sản phẩm thành công", updatedProduct));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi cập nhật sản phẩm: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa sản phẩm")
    public ResponseEntity<Response> deleteProduct(
            @Parameter(description = "ID của sản phẩm") @PathVariable("id") Integer id) {
        try {
            Optional<Product> optProduct = productService.findById(id);
            if (optProduct.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error("Không tìm thấy sản phẩm với ID: " + id));
            }
            
            Product product = optProduct.get();
            
            // Xóa hình ảnh nếu có
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                try {
                    storageService.delete(product.getImage());
                } catch (Exception e) {
                    // Log lỗi nhưng không dừng quá trình
                    System.err.println("Không thể xóa hình ảnh: " + e.getMessage());
                }
            }
            
            productService.deleteById(id);
            return ResponseEntity.ok(Response.success("Xóa sản phẩm thành công"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi xóa sản phẩm: " + e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm sản phẩm theo tên")
    public ResponseEntity<Response> searchProducts(
            @Parameter(description = "Từ khóa tìm kiếm") @RequestParam("name") String name) {
        try {
            List<Product> products = productService.findByProductNameContainingIgnoreCase(name);
            return ResponseEntity.ok(Response.success("Tìm kiếm sản phẩm thành công", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage()));
        }
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Lấy sản phẩm theo danh mục")
    public ResponseEntity<Response> getProductsByCategory(
            @Parameter(description = "ID của danh mục") @PathVariable("categoryId") Integer categoryId) {
        try {
            List<Product> products = productService.findByCategoryId(categoryId);
            return ResponseEntity.ok(Response.success("Lấy sản phẩm theo danh mục thành công", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy sản phẩm theo danh mục: " + e.getMessage()));
        }
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Lấy 4 sản phẩm mới nhất")
    public ResponseEntity<Response> getRecentProducts() {
        try {
            List<Product> products = productService.findTop4ByOrderByIdDesc();
            return ResponseEntity.ok(Response.success("Lấy sản phẩm mới nhất thành công", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy sản phẩm mới nhất: " + e.getMessage()));
        }
    }
    
    @GetMapping("/popular")
    @Operation(summary = "Lấy 4 sản phẩm bán chạy nhất")
    public ResponseEntity<Response> getPopularProducts() {
        try {
            List<Product> products = productService.findTop4ByOrderByPurchasesDesc();
            return ResponseEntity.ok(Response.success("Lấy sản phẩm bán chạy thành công", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy sản phẩm bán chạy: " + e.getMessage()));
        }
    }
}
