package vn.ngocthang.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.ngocthang.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Integer id);
    Product save(Product product);
    void deleteById(Integer id);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findTop4ByOrderByIdDesc();
    List<Product> findTop4ByOrderByPurchasesDesc();
    long count();
    Long sumPurchases();
}
