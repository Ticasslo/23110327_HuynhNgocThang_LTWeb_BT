package vn.ngocthang.controllers.api;

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
import vn.ngocthang.model.Response;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.StorageService;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category API", description = "API quản lý danh mục sản phẩm")
public class CategoryAPIController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private StorageService storageService;
    
    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả danh mục")
    public ResponseEntity<Response> getAllCategories() {
        try {
            System.out.println("=== API getAllCategories được gọi ===");
            List<Category> categories = categoryService.findAll();
            System.out.println("Số lượng categories: " + categories.size());
            for (Category cat : categories) {
                System.out.println("Category: " + cat.getCategoryName() + " (ID: " + cat.getId() + ")");
            }
            return ResponseEntity.ok(Response.success("Lấy danh sách danh mục thành công", categories));
        } catch (Exception e) {
            System.err.println("Lỗi trong getAllCategories: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy danh sách danh mục: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Lấy danh mục theo ID")
    public ResponseEntity<Response> getCategoryById(
            @Parameter(description = "ID của danh mục") @PathVariable("id") Integer id) {
        try {
            Optional<Category> category = categoryService.findById(id);
            if (category.isPresent()) {
                return ResponseEntity.ok(Response.success("Lấy danh mục thành công", category.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error("Không tìm thấy danh mục với ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi lấy danh mục: " + e.getMessage()));
        }
    }
    
    @PostMapping
    @Operation(summary = "Thêm danh mục mới")
    public ResponseEntity<Response> addCategory(
            @Parameter(description = "Tên danh mục") @RequestParam("categoryName") String categoryName,
            @Parameter(description = "Hình ảnh danh mục") @RequestParam(value = "images", required = false) MultipartFile images) {
        try {
            // Kiểm tra danh mục đã tồn tại chưa
            // Check if category exists by searching
            List<Category> existingCategories = categoryService.findByCategoryNameContainingIgnoreCase(categoryName);
            Optional<Category> existingCategory = existingCategories.stream()
                    .filter(c -> c.getCategoryName().equalsIgnoreCase(categoryName))
                    .findFirst();
            if (existingCategory.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Response.error("Danh mục '" + categoryName + "' đã tồn tại trong hệ thống"));
            }
            
            Category category = new Category();
            category.setCategoryName(categoryName);
            
            // Xử lý upload hình ảnh
            if (images != null && !images.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String fileName = storageService.getStorageFilename(images, uuid.toString());
                category.setImages(fileName);
                storageService.store(images, fileName);
            }
            
            Category savedCategory = categoryService.save(category);
            return ResponseEntity.ok(Response.success("Thêm danh mục thành công", savedCategory));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi thêm danh mục: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật danh mục")
    public ResponseEntity<Response> updateCategory(
            @Parameter(description = "ID của danh mục") @PathVariable("id") Integer id,
            @Parameter(description = "Tên danh mục") @RequestParam("categoryName") String categoryName,
            @Parameter(description = "Hình ảnh danh mục") @RequestParam(value = "images", required = false) MultipartFile images) {
        try {
            Optional<Category> optCategory = categoryService.findById(id);
            if (optCategory.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error("Không tìm thấy danh mục với ID: " + id));
            }
            
            Category category = optCategory.get();
            category.setCategoryName(categoryName);
            
            // Xử lý upload hình ảnh mới
            if (images != null && !images.isEmpty()) {
                // Xóa hình ảnh cũ nếu có
                if (category.getImages() != null && !category.getImages().isEmpty()) {
                    try {
                        storageService.delete(category.getImages());
                    } catch (Exception e) {
                        // Log lỗi nhưng không dừng quá trình
                        System.err.println("Không thể xóa hình ảnh cũ: " + e.getMessage());
                    }
                }
                
                UUID uuid = UUID.randomUUID();
                String fileName = storageService.getStorageFilename(images, uuid.toString());
                category.setImages(fileName);
                storageService.store(images, fileName);
            }
            
            Category updatedCategory = categoryService.save(category);
            return ResponseEntity.ok(Response.success("Cập nhật danh mục thành công", updatedCategory));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi cập nhật danh mục: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa danh mục")
    public ResponseEntity<Response> deleteCategory(
            @Parameter(description = "ID của danh mục") @PathVariable("id") Integer id) {
        try {
            Optional<Category> optCategory = categoryService.findById(id);
            if (optCategory.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error("Không tìm thấy danh mục với ID: " + id));
            }
            
            Category category = optCategory.get();
            
            // Xóa hình ảnh nếu có
            if (category.getImages() != null && !category.getImages().isEmpty()) {
                try {
                    storageService.delete(category.getImages());
                } catch (Exception e) {
                    // Log lỗi nhưng không dừng quá trình
                    System.err.println("Không thể xóa hình ảnh: " + e.getMessage());
                }
            }
            
            categoryService.deleteById(id);
            return ResponseEntity.ok(Response.success("Xóa danh mục thành công"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi xóa danh mục: " + e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm danh mục theo tên")
    public ResponseEntity<Response> searchCategories(
            @Parameter(description = "Từ khóa tìm kiếm") @RequestParam("name") String name) {
        try {
            List<Category> categories = categoryService.findByCategoryNameContainingIgnoreCase(name);
            return ResponseEntity.ok(Response.success("Tìm kiếm danh mục thành công", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error("Lỗi khi tìm kiếm danh mục: " + e.getMessage()));
        }
    }
}
