package vn.ngocthang.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.ngocthang.entity.Product;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.utils.Constants;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> recentProducts = productService.findTop4ByOrderByIdDesc();
        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("pageTitle", "Trang chủ - Product Manager");
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);
        
        return "web/home";
    }
    
    @GetMapping("/home")
    public String homePage(Model model) {
        List<Product> recentProducts = productService.findTop4ByOrderByIdDesc();
        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("pageTitle", "Trang chủ - Product Manager");
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);
        
        return "web/home";
    }
}
