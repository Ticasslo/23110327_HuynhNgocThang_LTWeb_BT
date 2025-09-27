package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.ngocthang.entity.Category;
import vn.ngocthang.services.CategoryService;

@Controller
public class ProductByCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products-by-category")
    public String productsByCategory(Model model) {
        // Load all categories for dropdown
        model.addAttribute("categories", categoryService.findAll());
        return "web/products-by-category";
    }

    @GetMapping("/products-by-category/{categoryId}")
    public String productsByCategoryId(@PathVariable("categoryId") Integer categoryId, Model model) {
        // Load specific category
        var category = categoryService.findById(categoryId);
        if (category.isPresent()) {
            model.addAttribute("selectedCategory", category.get());
        }
        
        // Load all categories for dropdown
        model.addAttribute("categories", categoryService.findAll());
        return "web/products-by-category";
    }
}
