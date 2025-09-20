package vn.ngocthang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.ngocthang.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    // Tìm kiếm sản phẩm theo tên
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    
    // Tìm kiếm sản phẩm theo tên và Phân trang
    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
    
    
    // Tìm sản phẩm theo category
    List<Product> findByCategoryId(Integer categoryId);
    
    // Lấy sản phẩm mới nhất
    @Query("SELECT p FROM Product p ORDER BY p.id DESC")
    List<Product> findTop4ByOrderByIdDesc();
    
    // Lấy sản phẩm bán chạy nhất
    @Query("SELECT p FROM Product p ORDER BY p.purchases DESC")
    List<Product> findTop4ByOrderByPurchasesDesc();
    
    // Tìm sản phẩm theo khoảng giá
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Tìm sản phẩm còn hàng
    List<Product> findByStockGreaterThan(Integer stock);
    
    // Đếm tổng số sản phẩm
    @Override
    long count();
    
    // Đếm tổng số lượng đã bán
    @Query("SELECT SUM(p.purchases) FROM Product p")
    Long sumPurchases();
    
    // Đếm tổng giá trị hàng tồn kho
    @Query("SELECT SUM(p.price * p.stock) FROM Product p")
    BigDecimal sumInventoryValue();
}
