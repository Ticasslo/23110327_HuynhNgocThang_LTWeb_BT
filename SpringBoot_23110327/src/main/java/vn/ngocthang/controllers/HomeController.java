package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.ngocthang.entity.Video;
import vn.ngocthang.repository.VideoRepository;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Video> recentVideos = videoRepository.findTop4ByOrderByIdDesc();
        model.addAttribute("recentVideos", recentVideos);
        model.addAttribute("pageTitle", "Trang chủ - Video Manager");
        return "web/home";
    }
    
    @GetMapping("/home")
    public String homePage(Model model) {
        List<Video> recentVideos = videoRepository.findTop4ByOrderByIdDesc();
        model.addAttribute("recentVideos", recentVideos);
        model.addAttribute("pageTitle", "Trang chủ - Video Manager");
        return "web/home";
    }
}
