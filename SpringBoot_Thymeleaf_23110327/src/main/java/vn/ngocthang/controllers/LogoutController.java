package vn.ngocthang.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ngocthang.utils.CookieUtils;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Xóa session
        request.getSession().invalidate();
        
        // Xóa cookie remember me
        CookieUtils.clearRememberMe(response);
        
        // Redirect về trang chủ
        return "redirect:/";
    }
}