package vn.ngocthang.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import vn.ngocthang.utils.SessionUtils;

@Controller
public class WaitingController {

    @GetMapping("/waiting")
    public String waiting(HttpServletRequest request) {
        // Kiểm tra đã đăng nhập chưa
        if (!SessionUtils.isLoggedIn(request)) {
            return "redirect:/login";
        }
        
        // Redirect dựa trên role
        if (SessionUtils.isAdmin(request)) {
            return "redirect:/admin";
        } else if (SessionUtils.isUser(request)) {
            return "redirect:/user";
        } else {
            return "redirect:/";
        }
    }
}
