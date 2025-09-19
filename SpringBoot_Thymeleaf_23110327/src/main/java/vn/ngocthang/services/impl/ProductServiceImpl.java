package vn.ngocthang.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ngocthang.entity.Product;
import vn.ngocthang.repository.ProductRepository;
import vn.ngocthang.services.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByProductNameContainingIgnoreCase(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    @Override
    public List<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String productName, String description) {
        return productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(productName, description);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findTop4ByOrderByIdDesc() {
        return productRepository.findTop4ByOrderByIdDesc();
    }

    @Override
    public List<Product> findTop4ByOrderByPurchasesDesc() {
        return productRepository.findTop4ByOrderByPurchasesDesc();
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public Long sumPurchases() {
        return productRepository.sumPurchases();
    }
}
