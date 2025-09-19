package vn.ngocthang.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ngocthang.entity.Product;
import vn.ngocthang.entity.Category;
import vn.ngocthang.repository.ProductRepository;
import vn.ngocthang.repository.CategoryRepository;
import vn.ngocthang.utils.UploadHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Product> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = productRepository.findByProductNameContainingIgnoreCase(keyword.trim());
        } else {
            items = productRepository.findAll();
        }

        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Quản lý sản phẩm");
        return "admin/products/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Thêm sản phẩm");
        return "admin/products/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Product product, 
                     @RequestParam("imageFile") MultipartFile imageFile,
                     @RequestParam("category_id") Integer categoryId) throws IOException {
        
        // Xử lý upload image
        if (!imageFile.isEmpty()) {
            String fileName = UploadHelper.save(imageFile);
            product.setImage(fileName);
        }
        
        // Set category
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isPresent()) {
            product.setCategory(categoryOpt.get());
        }
        
        // Set default values
        if (product.getPurchases() == null) {
            product.setPurchases(0L);
        }
        if (product.getStock() == null) {
            product.setStock(0);
        }
        if (product.getPrice() == null) {
            product.setPrice(BigDecimal.ZERO);
        }
        
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/products";
        }
        
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", opt.get());
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Chỉnh sửa sản phẩm");
        return "admin/products/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, 
                      @ModelAttribute Product input,
                      @RequestParam("imageFile") MultipartFile imageFile,
                      @RequestParam("category_id") Integer categoryId) throws IOException {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            Product exist = opt.get();
            exist.setProductName(input.getProductName());
            exist.setDescription(input.getDescription());
            exist.setPrice(input.getPrice());
            exist.setPurchases(input.getPurchases());
            exist.setStock(input.getStock());

            // Xử lý upload image mới
            if (!imageFile.isEmpty()) {
                // Xóa image cũ nếu có
                if (exist.getImage() != null && !exist.getImage().isEmpty()) {
                    UploadHelper.delete(exist.getImage());
                }
                String fileName = UploadHelper.save(imageFile);
                exist.setImage(fileName);
            } else if (input.getImage() == null || input.getImage().isEmpty()) {
                // Nếu không upload file mới và trường image trong form rỗng, xóa image cũ
                if (exist.getImage() != null && !exist.getImage().isEmpty()) {
                    UploadHelper.delete(exist.getImage());
                }
                exist.setImage(null);
            }
            
            // Set category
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            if (categoryOpt.isPresent()) {
                exist.setCategory(categoryOpt.get());
            }
            
            productRepository.save(exist);
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            Product product = opt.get();
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                UploadHelper.delete(product.getImage());
            }
            productRepository.deleteById(id);
        }
        return "redirect:/admin/products";
    }
}
