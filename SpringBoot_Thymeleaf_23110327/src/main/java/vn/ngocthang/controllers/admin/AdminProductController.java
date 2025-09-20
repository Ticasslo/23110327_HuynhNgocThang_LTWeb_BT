package vn.ngocthang.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.ngocthang.entity.Category;
import vn.ngocthang.entity.Product;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.UploadHelper;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;

    // Tự động thêm user vào tất cả các view
    @ModelAttribute("user")
    public Object addUserToModel(HttpServletRequest request) {
        return request.getSession().getAttribute(Constants.SESSION_ACCOUNT);
    }

    @GetMapping("")
    public String list(ModelMap model) {
        // Gọi hàm findAll() trong service
        List<Product> list = productService.findAll();
        // Chuyển dữ liệu từ list lên biến products
        model.addAttribute("products", list);
        return "admin/products/list";
    }

    @GetMapping("/add")
    public String add(ModelMap model) {
        List<Category> categories = categoryService.findAll();
        Product product = new Product();
        // Chuyển dữ liệu từ model vào biến product để đưa lên view
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/products/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @ModelAttribute("product") Product product, 
                                   @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                   @RequestParam("categoryId") Integer categoryId) {
        
        // Xử lý upload image
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UploadHelper.save(imageFile);
            product.setImage(fileName);
        }
        
        // Set category
        Category category = categoryService.findById(categoryId).orElse(null);
        product.setCategory(category);
        
        // Gọi hàm save trong service
        productService.save(product);
        
        // Đưa thông báo về cho biến message
        String message = "Product is saved!!!!!!!!";
        model.addAttribute("message", message);
        
        // Redirect về URL controller
        return new ModelAndView("redirect:/admin/products/searchpaginated");
    }

    @GetMapping("/edit/{productId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productId") Integer productId) {
        // Kiểm tra sự tồn tại của product
        Optional<Product> optProduct = productService.findById(productId);
        Product product = new Product();
        List<Category> categories = categoryService.findAll();
        
        if (optProduct.isPresent()) {
            // Copy từ entity sang product
            product = optProduct.get();
            // Đẩy dữ liệu ra view
            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            return new ModelAndView("admin/products/addOrEdit", model);
        } else {
            model.addAttribute("message", "Product is not existed!!!!");
            return new ModelAndView("redirect:/admin/products");
        }
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, ModelMap model) {
        Optional<Product> opt = productService.findById(id);
        if (opt.isPresent()) {
            Product product = opt.get();
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                UploadHelper.delete(product.getImage());
            }
            productService.deleteById(id);
        }
        return "redirect:/admin/products?success=1";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, ModelMap model) {
        List<Product> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = productService.findByProductNameContainingIgnoreCase(keyword.trim());
        } else {
            items = productService.findAll();
        }

        model.addAttribute("products", items);
        model.addAttribute("keyword", keyword);
        return "admin/products/search";
    }

    @GetMapping("/searchpaginated")
    public String searchPaginated(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 ModelMap model) {
        
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("productName"));
        Page<Product> resultPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            resultPage = productService.findByProductNameContainingIgnoreCase(keyword.trim(), pageable);
            model.addAttribute("keyword", keyword);
        } else {
            resultPage = productService.findAll(pageable);
        }
        
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, page - 2);
            int end = Math.min(page + 2, totalPages);
            
            if (end == totalPages) start = Math.max(1, end - 4);
            else if (start == 1) end = Math.min(start + 4, totalPages);
            
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        model.addAttribute("productPage", resultPage);
        return "admin/products/searchpaginated";
    }

}