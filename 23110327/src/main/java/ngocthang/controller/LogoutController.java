package ngocthang.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.CookieUtils;
import ngocthang.utils.SessionUtils;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Xóa session
        SessionUtils.invalidateSession(req);
        
        // Xóa Remember Me cookie
        CookieUtils.deleteRememberMe(resp);
        
        // Redirect về trang login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doGet(req, resp);
    }
}