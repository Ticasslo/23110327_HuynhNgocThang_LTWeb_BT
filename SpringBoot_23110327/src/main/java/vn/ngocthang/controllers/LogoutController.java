package vn.ngocthang.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vn.ngocthang.utils.CookieUtils;
import vn.ngocthang.utils.SessionUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Xóa session
        SessionUtils.invalidateSession(request);
        
        // Xóa Remember Me cookie
        CookieUtils.deleteRememberMe(response);
        
        // Redirect về trang login
        return "redirect:/login";
    }
    
    @PostMapping("/logout")
    public String logoutPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return logout(request, response);
    }
}
