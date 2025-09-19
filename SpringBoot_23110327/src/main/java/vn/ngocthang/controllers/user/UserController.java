package vn.ngocthang.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.ngocthang.entity.Video;
import vn.ngocthang.services.VideoService;

@Controller
public class UserController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/user")
    public String userHome(Model model) {
        // Lấy video mới nhất và xem nhiều nhất
        List<Video> recentVideos = videoService.findTop4ByOrderByIdDesc();
        List<Video> popularVideos = videoService.findTop4ByOrderByViewsDesc();
        
        model.addAttribute("recentVideos", recentVideos);
        model.addAttribute("popularVideos", popularVideos);
        model.addAttribute("pageTitle", "Trang người dùng - Video Manager");
        
        return "user/home";
    }
}
