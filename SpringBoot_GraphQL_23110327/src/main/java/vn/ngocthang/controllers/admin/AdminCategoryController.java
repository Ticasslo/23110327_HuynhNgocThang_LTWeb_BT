package vn.ngocthang.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vn.ngocthang.entity.Category;
import vn.ngocthang.entity.Product;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.UploadHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductService productService;

    // Tự động thêm user vào tất cả các view
    @ModelAttribute("user")
    public Object addUserToModel(HttpServletRequest request) {
        return request.getSession().getAttribute(Constants.SESSION_ACCOUNT);
    }

    @GetMapping("")
    public String list(ModelMap model) {
        // Gọi hàm findAll() trong service
        List<Category> list = categoryService.findAll();
        // Chuyển dữ liệu từ list lên biến categories
        model.addAttribute("categories", list);
        return "admin/categories/list";
    }

    @GetMapping("/add")
    public String add(ModelMap model) {
        Category category = new Category();
        // Chuyển dữ liệu từ model vào biến category để đưa lên view
        model.addAttribute("category", category);
        return "admin/categories/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @ModelAttribute("category") Category category, 
                                   @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        
        // Xử lý upload image
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UploadHelper.save(imageFile);
            category.setImages(fileName);
        }
        
        // Gọi hàm save trong service
        categoryService.save(category);
        
        // Đưa thông báo về cho biến message
        String message = "Category is saved!!!!!!!!";
        model.addAttribute("message", message);
        
        // Redirect về URL controller
        return new ModelAndView("redirect:/admin/categories/searchpaginated");
    }

    @GetMapping("/edit/{categoryId}")
    public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Integer categoryId) {
        // Kiểm tra sự tồn tại của category
        Optional<Category> optCategory = categoryService.findById(categoryId);
        Category category = new Category();
        
        if (optCategory.isPresent()) {
            // Copy từ entity sang category
            category = optCategory.get();
            // Đẩy dữ liệu ra view
            model.addAttribute("category", category);
            return new ModelAndView("admin/categories/addOrEdit", model);
        } else {
            model.addAttribute("message", "Category is not existed!!!!");
            return new ModelAndView("redirect:/admin/categories");
        }
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, ModelMap model) {
        // Kiểm tra xem category có product hay không
        List<Product> productsInCategory = productService.findByCategoryId(id);
        if (!productsInCategory.isEmpty()) {
            // Không cho phép xóa nếu còn product
            model.addAttribute("alert", "Không thể xóa danh mục này vì còn " + productsInCategory.size() + " sản phẩm thuộc danh mục này!");
            List<Category> items = categoryService.findAll();
            model.addAttribute("categories", items);
            return "admin/categories/list";
        }
        
        // Nếu không có product, cho phép xóa
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isPresent()) {
            Category category = opt.get();
            if (category.getImages() != null && !category.getImages().isEmpty()) {
                UploadHelper.delete(category.getImages());
            }
            categoryService.deleteById(id);
        }
        return "redirect:/admin/categories?success=1";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, ModelMap model) {
        List<Category> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = categoryService.findByCategoryNameContainingIgnoreCase(keyword.trim());
        } else {
            items = categoryService.findAll();
        }

        model.addAttribute("categories", items);
        model.addAttribute("keyword", keyword);
        return "admin/categories/search";
    }

    @GetMapping("/searchpaginated")
    public String searchPaginated(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 ModelMap model) {
        
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("categoryName"));
        Page<Category> resultPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            resultPage = categoryService.findByCategoryNameContainingIgnoreCase(keyword.trim(), pageable);
            model.addAttribute("keyword", keyword);
        } else {
            resultPage = categoryService.findAll(pageable);
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
        
        model.addAttribute("categoryPage", resultPage);
        return "admin/categories/searchpaginated";
    }

}
