package vn.ngocthang.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.ngocthang.entity.Product;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.services.UserService;
import vn.ngocthang.utils.Constants;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;

    @GetMapping({"", "/", "/home"})
    public String home(Model model, jakarta.servlet.http.HttpServletRequest request) {
        // Lấy thống kê
        long totalProducts = productService.count();
        long totalCategories = categoryService.count();
        long totalUsers = userService.findAll().size();
        Long totalPurchases = productService.sumPurchases();
        
        // Lấy sản phẩm mới nhất
        List<Product> recentProducts = productService.findTop4ByOrderByIdDesc();
        
        // Thêm vào model
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPurchases", totalPurchases != null ? totalPurchases : 0);
        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("pageTitle", "Dashboard - Admin Panel");
        
        // Thêm thông tin user đăng nhập
        Object user = request.getSession().getAttribute(Constants.SESSION_ACCOUNT);
        model.addAttribute("user", user);
        
        return "admin/home";
    }
}
