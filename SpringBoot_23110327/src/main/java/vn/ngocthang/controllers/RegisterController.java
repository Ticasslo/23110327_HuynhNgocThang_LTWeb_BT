package vn.ngocthang.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ngocthang.entity.User;
import vn.ngocthang.services.UserService;
import vn.ngocthang.utils.CookieUtils;
import vn.ngocthang.utils.SessionUtils;

import java.io.IOException;

@Controller
public class RegisterController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        // Kiểm tra đã đăng nhập chưa
        if (SessionUtils.isLoggedIn(request)) {
            return "redirect:/waiting";
        }

        // Check cookie remember me - tự động đăng nhập nếu có
        String rememberUsername = CookieUtils.getRememberMeUsername(request);
        if (rememberUsername != null) {
            User user = userService.get(rememberUsername);
            if (user != null) {
                SessionUtils.setUser(request, user);
                return "redirect:/waiting";
            }
        }
        
        return "web/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String email,
                          @RequestParam String fullname,
                          @RequestParam String phone,
                          Model model) {
        
        String alertMsg = "";

        // Check email tồn tại
        if (userService.checkExistEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            model.addAttribute("alert", alertMsg);
            return "web/register";
        }

        // Check username tồn tại
        if (userService.checkExistUsername(username)) {
            alertMsg = "Tài khoản đã tồn tại!";
            model.addAttribute("alert", alertMsg);
            return "web/register";
        }

        // Đăng ký
        boolean isSuccess = userService.register(email, password, username, fullname, phone);
        if (isSuccess) {
            return "redirect:/login";
        } else {
            alertMsg = "System error!";
            model.addAttribute("alert", alertMsg);
            return "web/register";
        }
    }
}
