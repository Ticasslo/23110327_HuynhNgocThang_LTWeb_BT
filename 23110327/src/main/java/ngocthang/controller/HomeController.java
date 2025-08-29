package ngocthang.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Kiểm tra role member
        Object account = req.getSession().getAttribute("account");
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}