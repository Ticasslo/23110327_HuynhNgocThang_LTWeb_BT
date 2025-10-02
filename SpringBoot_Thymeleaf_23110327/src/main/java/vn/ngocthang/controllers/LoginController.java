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
import vn.ngocthang.dto.UserLoginDto;
import vn.ngocthang.entity.User;
import vn.ngocthang.services.UserService;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.CookieUtils;
import vn.ngocthang.utils.SessionUtils;

@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        // Kiểm tra đã đăng nhập chưa
        if (SessionUtils.isLoggedIn(request)) {
            return "redirect:/waiting";
        }

        // Check cookie remember me
        String rememberUsername = CookieUtils.getRememberMeUsername(request);
        if (rememberUsername != null) {
            // Tự động đăng nhập từ cookie
            User user = userService.get(rememberUsername);
            if (user != null) {
                SessionUtils.setUser(request, user);
                return "redirect:/waiting";
            }
        }

        model.addAttribute("pageTitle", "Đăng nhập - Product Manager");
        model.addAttribute("userLoginDto", new UserLoginDto());
        
        // Thêm thông tin tác giả
        model.addAttribute("authorName", Constants.AUTHOR_NAME);
        model.addAttribute("authorAvatar", Constants.AUTHOR_AVATAR);
        model.addAttribute("authorStudentId", Constants.AUTHOR_STUDENT_ID);
        
        return "web/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLoginDto") UserLoginDto userLoginDto,
                       BindingResult bindingResult,
                       HttpServletRequest request, 
                       HttpServletResponse response,
                       Model model) throws IOException {
        
        // Kiểm tra validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Đăng nhập - Product Manager");
            return "web/login";
        }

        // Xử lý đăng nhập
        User user = userService.login(userLoginDto.getUsername().trim(), userLoginDto.getPassword());

        if (user != null) {
            // Đăng nhập thành công
            SessionUtils.setUser(request, user);

            // Lưu cookie remember me nếu được chọn
            if ("on".equals(userLoginDto.getRemember())) {
                CookieUtils.saveRememberMe(response, userLoginDto.getUsername());
            }

            // Kiểm tra có URL redirect từ interceptor không
            String redirectUrl = SessionUtils.getRedirectUrl(request);
            if (redirectUrl != null) {
                SessionUtils.removeRedirectUrl(request);
                return "redirect:" + redirectUrl;
            } else {
                // Redirect mặc định
                return "redirect:/waiting";
            }

        } else {
            // Đăng nhập thất bại
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
            model.addAttribute("pageTitle", "Đăng nhập - Product Manager");
            return "web/login";
        }
    }
}
