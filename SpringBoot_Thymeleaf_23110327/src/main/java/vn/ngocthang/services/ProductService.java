package vn.ngocthang.services;

import vn.ngocthang.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Integer id);
    Product save(Product product);
    void deleteById(Integer id);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String productName, String description);
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findTop4ByOrderByIdDesc();
    List<Product> findTop4ByOrderByPurchasesDesc();
    long count();
    Long sumPurchases();
}
