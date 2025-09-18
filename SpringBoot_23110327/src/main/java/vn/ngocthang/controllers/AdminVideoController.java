package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ngocthang.entity.Video;
import vn.ngocthang.entity.Category;
import vn.ngocthang.repository.VideoRepository;
import vn.ngocthang.repository.CategoryRepository;
import vn.ngocthang.utils.UploadHelper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/videos")
public class AdminVideoController {

    @Autowired
    private VideoRepository videoRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Video> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = videoRepository.findByTitleContainingIgnoreCase(keyword.trim());
        } else {
            items = videoRepository.findAll();
        }

        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Quản lý video");
        return "admin/videos/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("video", new Video());
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Thêm video");
        return "admin/videos/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Video video, 
                     @RequestParam("posterFile") MultipartFile posterFile,
                     @RequestParam("category_id") Integer categoryId) throws IOException {
        
        // Xử lý upload poster
        if (!posterFile.isEmpty()) {
            String fileName = UploadHelper.save(posterFile);
            video.setPoster(fileName);
        }
        
        // Set category
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isPresent()) {
            video.setCategory(categoryOpt.get());
        }
        
        // Set views mặc định
        if (video.getViews() == null) {
            video.setViews(0L);
        }
        
        videoRepository.save(video);
        return "redirect:/admin/videos";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Optional<Video> opt = videoRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/videos";
        }
        
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("video", opt.get());
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Chỉnh sửa video");
        return "admin/videos/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, 
                      @ModelAttribute Video input,
                      @RequestParam("posterFile") MultipartFile posterFile,
                      @RequestParam("category_id") Integer categoryId) throws IOException {
        Optional<Video> opt = videoRepository.findById(id);
        if (opt.isPresent()) {
            Video exist = opt.get();
            exist.setTitle(input.getTitle());
            exist.setDescription(input.getDescription());
            
            // Cập nhật views nếu có
            if (input.getViews() != null) {
                exist.setViews(input.getViews());
            }

            // Xử lý upload poster mới
            if (!posterFile.isEmpty()) {
                // Xóa poster cũ nếu có
                if (exist.getPoster() != null && !exist.getPoster().isEmpty()) {
                    UploadHelper.delete(exist.getPoster());
                }
                String fileName = UploadHelper.save(posterFile);
                exist.setPoster(fileName);
            } else if (input.getPoster() == null || input.getPoster().isEmpty()) {
                // Nếu không upload file mới và trường poster trong form rỗng, xóa poster cũ
                if (exist.getPoster() != null && !exist.getPoster().isEmpty()) {
                    UploadHelper.delete(exist.getPoster());
                }
                exist.setPoster(null);
            }
            
            // Set category
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            if (categoryOpt.isPresent()) {
                exist.setCategory(categoryOpt.get());
            }
            
            videoRepository.save(exist);
        }
        return "redirect:/admin/videos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Optional<Video> opt = videoRepository.findById(id);
        if (opt.isPresent()) {
            Video video = opt.get();
            if (video.getPoster() != null && !video.getPoster().isEmpty()) {
                UploadHelper.delete(video.getPoster());
            }
            videoRepository.deleteById(id);
        }
        return "redirect:/admin/videos";
    }
}
