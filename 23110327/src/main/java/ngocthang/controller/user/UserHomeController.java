package ngocthang.controller.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.SessionUtils;

@WebServlet(urlPatterns = "/user/home")
public class UserHomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Kiểm tra role user
        if (!SessionUtils.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        req.getRequestDispatcher("/views/user/home.jsp").forward(req, resp);
    }
} 