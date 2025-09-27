package ngocthang.controllers.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.Constant_23110327;
import ngocthang.utils.SessionUtils_23110327;

@WebServlet(urlPatterns = {"/user/home"})
public class UserHomeController_23110327 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionUtils_23110327.isLoggedIn(req)) {
            SessionUtils_23110327.setRedirectUrl(req, req.getRequestURI());
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher(Constant_23110327.Path.USER_HOME).forward(req, resp);
    }
}


