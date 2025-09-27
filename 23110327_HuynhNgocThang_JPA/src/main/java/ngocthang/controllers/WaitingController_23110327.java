package ngocthang.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.SessionUtils_23110327;

@WebServlet(urlPatterns = {"/waiting"})
public class WaitingController_23110327 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!SessionUtils_23110327.isLoggedIn(req)) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (SessionUtils_23110327.isAdmin(req)) {
			resp.sendRedirect(req.getContextPath() + "/admin/home");
			return;
		}

		// Mặc định là user
		resp.sendRedirect(req.getContextPath() + "/user/home");
	}
}


