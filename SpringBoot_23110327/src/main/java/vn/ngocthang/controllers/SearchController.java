package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ngocthang.entity.Video;
import vn.ngocthang.repository.VideoRepository;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping({"/search", "/user/search"})
    public String search(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Tìm kiếm video theo title hoặc description
            List<Video> videos = videoRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                keyword.trim(), keyword.trim());
            model.addAttribute("videos", videos);
        }
        model.addAttribute("pageTitle", "Tìm kiếm - Video Manager");
        return "web/search";
    }
}
