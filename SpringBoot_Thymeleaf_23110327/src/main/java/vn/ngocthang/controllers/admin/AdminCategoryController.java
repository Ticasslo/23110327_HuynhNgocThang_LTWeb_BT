package vn.ngocthang.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ngocthang.entity.Category;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.utils.UploadHelper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Category> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = categoryService.findByCategoryNameContainingIgnoreCase(keyword.trim());
        } else {
            items = categoryService.findAll();
        }

        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Quản lý danh mục");
        return "admin/categories/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Thêm danh mục");
        return "admin/categories/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Category category, 
                     @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        
        // Xử lý upload image
        if (!imageFile.isEmpty()) {
            String fileName = UploadHelper.save(imageFile);
            category.setImages(fileName);
        }
        
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/categories";
        }
        
        model.addAttribute("category", opt.get());
        model.addAttribute("pageTitle", "Chỉnh sửa danh mục");
        return "admin/categories/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, 
                      @ModelAttribute Category input,
                      @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isPresent()) {
            Category exist = opt.get();
            exist.setCategoryName(input.getCategoryName());

            // Xử lý upload image mới
            if (!imageFile.isEmpty()) {
                // Xóa image cũ nếu có
                if (exist.getImages() != null && !exist.getImages().isEmpty()) {
                    UploadHelper.delete(exist.getImages());
                }
                String fileName = UploadHelper.save(imageFile);
                exist.setImages(fileName);
            } else if (input.getImages() == null || input.getImages().isEmpty()) {
                // Nếu không upload file mới và trường images trong form rỗng, xóa image cũ
                if (exist.getImages() != null && !exist.getImages().isEmpty()) {
                    UploadHelper.delete(exist.getImages());
                }
                exist.setImages(null);
            }
            
            categoryService.save(exist);
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isPresent()) {
            Category category = opt.get();
            if (category.getImages() != null && !category.getImages().isEmpty()) {
                UploadHelper.delete(category.getImages());
            }
            categoryService.deleteById(id);
        }
        return "redirect:/admin/categories";
    }
}
