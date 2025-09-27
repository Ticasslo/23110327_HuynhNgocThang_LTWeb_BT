package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.User;
import ngocthang.services.IUserService_23110327;
import ngocthang.services.impl.UserServiceImpl_23110327;
import ngocthang.utils.Constant_23110327;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController_23110327 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_23110327 userService_23110327 = new UserServiceImpl_23110327();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Constant_23110327.Path.REGISTER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phoneStr = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        // Validation
        if (email == null || email.trim().isEmpty() || 
            fullname == null || fullname.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            req.setAttribute("alert", "Vui lòng điền đầy đủ thông tin");
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phoneStr);
            req.getRequestDispatcher(Constant_23110327.Path.REGISTER).forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("alert", "Mật khẩu xác nhận không khớp");
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phoneStr);
            req.getRequestDispatcher(Constant_23110327.Path.REGISTER).forward(req, resp);
            return;
        }

        // Kiểm tra email đã tồn tại
        if (userService_23110327.getByUsernameOrEmail(email.trim()) != null) {
            req.setAttribute("alert", "Email đã tồn tại");
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phoneStr);
            req.getRequestDispatcher(Constant_23110327.Path.REGISTER).forward(req, resp);
            return;
        }

        // Tạo user mới
        User newUser = new User();
        newUser.setEmail(email.trim());
        newUser.setFullname(fullname.trim());
        newUser.setPassword(password);
        newUser.setAdmin(false); // Mặc định là user
        newUser.setSignupDate(java.time.LocalDateTime.now()); // Ngày đăng ký
        newUser.setLastLogin(null); // Chưa đăng nhập lần nào
        
        try {
            if (phoneStr != null && !phoneStr.trim().isEmpty()) {
                newUser.setPhone(Integer.parseInt(phoneStr));
            }
        } catch (NumberFormatException e) {
            req.setAttribute("alert", "Số điện thoại không hợp lệ");
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phoneStr);
            req.getRequestDispatcher(Constant_23110327.Path.REGISTER).forward(req, resp);
            return;
        }

        // Lưu user
        try {
            userService_23110327.save(newUser);
            req.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            req.getRequestDispatcher(Constant_23110327.Path.LOGIN).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("alert", "Có lỗi xảy ra khi đăng ký: " + e.getMessage());
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phoneStr);
            req.getRequestDispatcher(Constant_23110327.Path.REGISTER).forward(req, resp);
        }
    }
}
