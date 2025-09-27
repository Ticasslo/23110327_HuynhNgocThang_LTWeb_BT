package ngocthang.controllers.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.Constant_23110327;
import ngocthang.utils.SessionUtils_23110327;

@WebServlet(urlPatterns = {"/admin/home"})
public class AdminHomeController_23110327 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils_23110327.isLoggedIn(req)) {
            SessionUtils_23110327.setRedirectUrl(req, req.getRequestURI());
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (!SessionUtils_23110327.isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/user/home");
            return;
        }

        req.getRequestDispatcher(Constant_23110327.Path.ADMIN_HOME).forward(req, resp);
    }
}


