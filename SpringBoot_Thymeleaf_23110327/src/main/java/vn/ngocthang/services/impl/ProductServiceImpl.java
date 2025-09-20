package vn.ngocthang.services.impl;

// import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        // Gọi hàm findAll() trong repository
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        // Nếu là entity mới (id == 0)
        if (product.getId() == 0) {
            return productRepository.save(product);
        } else {
            // Nếu là update entity
            Optional<Product> opt = findById(product.getId());
            if (opt.isPresent()) {
                // Nếu tên sản phẩm trống, giữ lại tên cũ
                if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
                    product.setProductName(opt.get().getProductName());
                }
                // Nếu mô tả trống, giữ lại mô tả cũ
                if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
                    product.setDescription(opt.get().getDescription());
                }
                // Nếu image trống, giữ lại image cũ
                if (product.getImage() == null || product.getImage().trim().isEmpty()) {
                    product.setImage(opt.get().getImage());
                }
                // Nếu giá null, giữ lại giá cũ
                if (product.getPrice() == null) {
                    product.setPrice(opt.get().getPrice());
                }
                // Nếu stock null, giữ lại stock cũ
                if (product.getStock() == null) {
                    product.setStock(opt.get().getStock());
                }
                // Nếu category null, giữ lại category cũ
                if (product.getCategory() == null) {
                    product.setCategory(opt.get().getCategory());
                }
                // Nếu purchases null, giữ lại purchases cũ
                if (product.getPurchases() == null) {
                    product.setPurchases(opt.get().getPurchases());
                }
            }
            return productRepository.save(product);
        }
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
    public Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCase(productName, pageable);
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
