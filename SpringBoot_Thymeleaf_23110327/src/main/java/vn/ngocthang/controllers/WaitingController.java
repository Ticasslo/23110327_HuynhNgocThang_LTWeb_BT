package vn.ngocthang.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.SessionUtils;

@Controller
public class WaitingController {

    @GetMapping("/waiting")
    public String waiting(HttpServletRequest request, Model model) {
        // Kiểm tra đã đăng nhập chưa
        if (!SessionUtils.isLoggedIn(request)) {
            return "redirect:/login";
        }

        // Lấy thông tin user từ session
        var user = SessionUtils.getUser(request);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "Chào mừng - Product Manager");
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);

        // Redirect dựa trên role
        if (user.getRoleid() == 1) {
            return "redirect:/admin";
        } else {
            return "redirect:/user";
        }
    }
}