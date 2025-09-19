package vn.ngocthang.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.ngocthang.repository.CategoryRepository;
import vn.ngocthang.repository.UserRepository;
import vn.ngocthang.repository.VideoRepository;

@Controller
public class AdminController {

    @Autowired
    private VideoRepository videoRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String adminHome(Model model) {
        // Thống kê cho admin dashboard
        long totalVideos = videoRepository.count();
        long totalCategories = categoryRepository.count();
        long totalUsers = userRepository.count();
        Long totalViews = videoRepository.sumViews();
        
        model.addAttribute("totalVideos", totalVideos);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalViews", totalViews != null ? totalViews : 0);
        
        return "admin/home";
    }
}
