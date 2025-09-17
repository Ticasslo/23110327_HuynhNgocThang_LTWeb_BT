package vn.ngocthang.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.SessionUtils;

@Controller
public class WaitingController {

    @GetMapping("/waiting")
    public String waiting(HttpServletRequest request) {
        // Kiểm tra đã đăng nhập chưa
        if (!SessionUtils.isLoggedIn(request)) {
            return "redirect:/login";
        }

        // Redirect theo role
        if (SessionUtils.isAdmin(request)) {
            return "redirect:/admin";
        } else if (SessionUtils.isUser(request)) {
            return "redirect:/user";
        } else {
            // Nếu role không hợp lệ, đăng xuất
            SessionUtils.invalidateSession(request);
            return "redirect:/login";
        }
    }
}
