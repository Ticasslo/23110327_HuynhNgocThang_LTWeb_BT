package vn.ngocthang.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ngocthang.entity.Category;
import vn.ngocthang.entity.Video;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.VideoService;
import vn.ngocthang.utils.UploadHelper;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private VideoService videoService;

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
    public String add(@ModelAttribute Category category, @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String saved = UploadHelper.save(file);
            category.setImages(saved);
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
    public String edit(@PathVariable("id") Integer id, @ModelAttribute Category input, @RequestParam(value = "file", required = false) MultipartFile file) {
        Optional<Category> opt = categoryService.findById(id);
        if (opt.isPresent()) {
            Category exist = opt.get();
            exist.setCategoryName(input.getCategoryName());
            if (file != null && !file.isEmpty()) {
                String saved = UploadHelper.save(file);
                exist.setImages(saved);
            }
            categoryService.save(exist);
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        // Kiểm tra xem category có video hay không
        List<Video> videosInCategory = videoService.findByCategoryId(id);
        if (!videosInCategory.isEmpty()) {
            // Không cho phép xóa nếu còn video
            model.addAttribute("alert", "Không thể xóa danh mục này vì còn " + videosInCategory.size() + " video thuộc danh mục này!");
            List<Category> items = categoryService.findAll();
            model.addAttribute("items", items);
            model.addAttribute("pageTitle", "Quản lý danh mục");
            return "admin/categories/list";
        }
        
        // Nếu không có video, cho phép xóa
        categoryService.deleteById(id);
        return "redirect:/admin/categories?success=1";
    }
}


