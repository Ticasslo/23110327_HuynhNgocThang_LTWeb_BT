package ngocthang.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.services.IBookService_23110327;
import ngocthang.services.impl.BookServiceImpl_23110327;

@WebServlet(urlPatterns = {"/booklist"})
public class BookListController_23110327 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IBookService_23110327 iBookService_23110327 = new BookServiceImpl_23110327();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int size = parseIntOrDefault(req.getParameter("size"), 6);

        long total = iBookService_23110327.countAll();
        long totalPages = (long) Math.ceil((double) total / size);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = (int) totalPages;

        List<Object[]> bookData = iBookService_23110327.findBooksWithAuthorsAndReviewCount(page, size);
        req.setAttribute("bookData", bookData);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/views/books/list.jsp").forward(req, resp);
    }

    private static int parseIntOrDefault(String s, int d) {
        try { return Integer.parseInt(s); } catch (Exception e) { return d; }
    }
}


