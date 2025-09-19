package vn.ngocthang.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.ngocthang.services.CategoryService;
import vn.ngocthang.services.UserService;
import vn.ngocthang.services.VideoService;

@Controller
public class AdminController {

    @Autowired
    private VideoService videoService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminHome(Model model) {
        // Thống kê cho admin dashboard
        long totalVideos = videoService.count();
        long totalCategories = categoryService.findAll().size();
        long totalUsers = userService.findAll().size();
        Long totalViews = videoService.sumViews();
        
        model.addAttribute("totalVideos", totalVideos);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalViews", totalViews != null ? totalViews : 0);
        
        return "admin/home";
    }
}
