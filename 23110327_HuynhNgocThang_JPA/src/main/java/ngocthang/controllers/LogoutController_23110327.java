package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.CookieUtils_23110327;
import ngocthang.utils.SessionUtils_23110327;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutController_23110327 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SessionUtils_23110327.invalidateSession(req);
		CookieUtils_23110327.deleteRememberMe(resp);
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}


