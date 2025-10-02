package vn.ngocthang.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import vn.ngocthang.dto.UserRegistrationDto;
import vn.ngocthang.entity.User;
import vn.ngocthang.services.UserService;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.CookieUtils;
import vn.ngocthang.utils.SessionUtils;

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
        
        model.addAttribute("pageTitle", "Đăng ký - Product Manager");
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);
        
        return "web/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto,
                          BindingResult bindingResult,
                          Model model) {
        
        // Kiểm tra validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Đăng ký - Product Manager");
            return "web/register";
        }
        
        // Check email tồn tại
        if (userService.checkExistEmail(userRegistrationDto.getEmail())) {
            model.addAttribute("alert", "Email đã tồn tại!");
            model.addAttribute("pageTitle", "Đăng ký - Product Manager");
            return "web/register";
        }

        // Check username tồn tại
        if (userService.checkExistUsername(userRegistrationDto.getUsername())) {
            model.addAttribute("alert", "Tài khoản đã tồn tại!");
            model.addAttribute("pageTitle", "Đăng ký - Product Manager");
            return "web/register";
        }

        // Đăng ký
        boolean isSuccess = userService.register(
            userRegistrationDto.getEmail(), 
            userRegistrationDto.getPassword(), 
            userRegistrationDto.getUsername(), 
            userRegistrationDto.getFullname(), 
            userRegistrationDto.getPhone()
        );
        
        if (isSuccess) {
            return "redirect:/login";
        } else {
            model.addAttribute("alert", "System error!");
            model.addAttribute("pageTitle", "Đăng ký - Product Manager");
            return "web/register";
        }
    }
}
