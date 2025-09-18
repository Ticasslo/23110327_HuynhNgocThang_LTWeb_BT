package vn.ngocthang.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.ngocthang.entity.User;
import vn.ngocthang.repository.UserRepository;
import vn.ngocthang.utils.RandomUtils;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> items;
        if (keyword != null && !keyword.trim().isEmpty()) {
            items = userRepository.findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(keyword.trim(), keyword.trim());
        } else {
            items = userRepository.findAll();
        }

        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Quản lý người dùng");
        return "admin/users/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Thêm người dùng");
        return "admin/users/form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute User user) {
        // Tạo ID mới
        int newId = RandomUtils.getRandomId();
        while (userRepository.existsById(newId)) {
            newId = RandomUtils.getRandomId();
        }
        user.setId(newId);
        
        // Set mật khẩu mặc định nếu không có
        if (user.getPassWord() == null || user.getPassWord().trim().isEmpty()) {
            user.setPassWord("123456"); // Mật khẩu mặc định
        }
        
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", opt.get());
        model.addAttribute("pageTitle", "Chỉnh sửa người dùng");
        return "admin/users/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, @ModelAttribute User input) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            User exist = opt.get();
            exist.setFullName(input.getFullName());
            exist.setEmail(input.getEmail());
            exist.setUserName(input.getUserName());
            exist.setPhone(input.getPhone());
            exist.setRoleid(input.getRoleid());
            // Không cập nhật password và avatar ở đây
            userRepository.save(exist);
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
