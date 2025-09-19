package vn.ngocthang.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ngocthang.utils.CookieUtils;
import vn.ngocthang.utils.SessionUtils;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Xóa session
        SessionUtils.invalidateSession(request);
        
        // Xóa remember me cookie
        CookieUtils.clearRememberMe(response);
        
        return "redirect:/login";
    }
}
