package vn.ngocthang.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.ngocthang.entity.Video;
import vn.ngocthang.services.VideoService;
import vn.ngocthang.utils.Constants;

@Controller
public class HomeController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/")
    public String home(Model model) {
        List<Video> recentVideos = videoService.findTop4ByOrderByIdDesc();
        model.addAttribute("recentVideos", recentVideos);
        model.addAttribute("pageTitle", "Trang chủ - Video Manager");
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);
        
        return "web/home";
    }
    
    @GetMapping("/home")
    public String homePage(Model model) {
        List<Video> recentVideos = videoService.findTop4ByOrderByIdDesc();
        model.addAttribute("recentVideos", recentVideos);
        model.addAttribute("pageTitle", "Trang chủ - Video Manager");
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);
        
        return "web/home";
    }
}
