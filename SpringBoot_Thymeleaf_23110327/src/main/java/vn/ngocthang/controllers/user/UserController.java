package vn.ngocthang.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.ngocthang.entity.Product;
import vn.ngocthang.services.ProductService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String userHome(Model model) {
        // Lấy sản phẩm mới nhất và bán chạy
        List<Product> recentProducts = productService.findTop4ByOrderByIdDesc();
        List<Product> popularProducts = productService.findTop4ByOrderByPurchasesDesc();
        
        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("popularProducts", popularProducts);
        model.addAttribute("pageTitle", "Trang người dùng - Product Manager");
        
        return "user/home";
    }
}
