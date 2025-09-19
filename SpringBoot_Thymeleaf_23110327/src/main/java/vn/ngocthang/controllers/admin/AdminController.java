package vn.ngocthang.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.services.UserService;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminHome(Model model) {
        // Thống kê cho admin dashboard
        long totalProducts = productService.count();
        long totalCategories = categoryService.findAll().size();
        long totalUsers = userService.findAll().size();
        Long totalPurchases = productService.sumPurchases();
        
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPurchases", totalPurchases != null ? totalPurchases : 0);
        
        return "admin/home";
    }
}
