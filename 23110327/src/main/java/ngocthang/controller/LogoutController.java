package ngocthang.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ngocthang.utils.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Xóa session
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // Xóa toàn bộ session
        }
        
        // Xóa tất cả cookies liên quan
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Xóa cookie remember
                if (cookie.getName().equals("username") || 
                    cookie.getName().equals(Constant.COOKIE_REMEMBER)) {
                    cookie.setValue("");
                    cookie.setMaxAge(0); // Xóa cookie
                    cookie.setPath("/"); // Đảm bảo path đúng
                    resp.addCookie(cookie);
                }
            }
        }
        
        // Redirect về trang login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doGet(req, resp);
    }
}