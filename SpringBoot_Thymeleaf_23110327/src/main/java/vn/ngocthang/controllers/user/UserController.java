package vn.ngocthang.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import vn.ngocthang.entity.Product;
import vn.ngocthang.services.ProductService;
import vn.ngocthang.utils.Constants;
import vn.ngocthang.utils.SessionUtils;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;

    @GetMapping({"", "/", "/home"})
    public String home(HttpServletRequest request, Model model) {
        // Kiểm tra đã đăng nhập chưa
        if (!SessionUtils.isLoggedIn(request)) {
            return "redirect:/login";
        }

        // Lấy thông tin user từ session
        Object userObj = request.getSession().getAttribute(Constants.SESSION_ACCOUNT);
        if (userObj != null) {
            model.addAttribute("user", userObj);
        }

        // Lấy sản phẩm mới nhất
        List<Product> recentProducts = productService.findTop4ByOrderByIdDesc();
        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("pageTitle", "Trang cá nhân - Product Manager");
        
        return "user/home";
    }
}