package vn.ngocthang.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.ngocthang.entity.Product;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.utils.Constants;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Tìm kiếm sản phẩm theo tên
            List<Product> products = productService.findByProductNameContainingIgnoreCase(keyword.trim());
            model.addAttribute("products", products);
        }
        model.addAttribute("pageTitle", "Tìm kiếm - Product Manager");
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);
        
        return "web/search";
    }
    
    @GetMapping("/user/search")
    public String userSearch(@RequestParam(required = false) String keyword, Model model, 
                            jakarta.servlet.http.HttpServletRequest request) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Tìm kiếm sản phẩm theo tên
            List<Product> products = productService.findByProductNameContainingIgnoreCase(keyword.trim());
            model.addAttribute("products", products);
        }
        model.addAttribute("pageTitle", "Tìm kiếm - Product Manager");
        
        // Thêm thông tin user đăng nhập
        Object user = request.getSession().getAttribute(Constants.SESSION_ACCOUNT);
        model.addAttribute("user", user);
        
        return "user/search";
    }
}
