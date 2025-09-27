package ngocthang.controllers.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.Author;
import ngocthang.services.IAuthorService_23110327;
import ngocthang.services.impl.AuthorServiceImpl_23110327;

@WebServlet(urlPatterns = {
    "/admin/authors",
    "/admin/authors/add",
    "/admin/authors/saveOrUpdate",
    "/admin/authors/edit",
    "/admin/authors/delete"
})
public class AdminAuthorController_23110327 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IAuthorService_23110327 authorService_23110327 = new AuthorServiceImpl_23110327();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/admin/authors")) {
            handleSearchPaginated(req, resp);
            return;
        }
        if (uri.endsWith("/admin/authors/add")) {
            req.setAttribute("author", new Author());
            req.getRequestDispatcher("/views/admin/authors/addOrEdit.jsp").forward(req, resp);
            return;
        }
        if (uri.endsWith("/admin/authors/edit")) {
            String idStr = req.getParameter("id");
            try {
                Integer id = Integer.parseInt(idStr);
                Author author = authorService_23110327.findById(id);
                if (author != null) {
                    req.setAttribute("author", author);
                    req.getRequestDispatcher("/views/admin/authors/addOrEdit.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception ignored) {}
            resp.sendRedirect(req.getContextPath() + "/admin/authors");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/admin/authors/saveOrUpdate")) {
            handleSaveOrUpdate(req, resp);
            return;
        }
        if (uri.endsWith("/admin/authors/delete")) {
            String idStr = req.getParameter("id");
            try {
                Integer id = Integer.parseInt(idStr);
                authorService_23110327.deleteById(id);
            } catch (Exception ignored) {}
            resp.sendRedirect(req.getContextPath() + "/admin/authors");
        }
    }

    private void handleSearchPaginated(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int size = parseIntOrDefault(req.getParameter("size"), 5);

        List<Author> authors;
        long totalItems;
        if (keyword != null && !keyword.trim().isEmpty()) {
            authors = authorService_23110327.findByAuthorNameLikePaged(keyword.trim(), page, size);
            totalItems = authorService_23110327.countByAuthorNameLike(keyword.trim());
            req.setAttribute("keyword", keyword.trim());
        } else {
            authors = authorService_23110327.findAllPaged(page, size);
            totalItems = authorService_23110327.countAll();
        }

        long totalPages = (long) Math.ceil((double) totalItems / size);
        if (totalPages == 0) totalPages = 1;

        req.setAttribute("authors", authors);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/views/admin/authors/searchpaginated.jsp").forward(req, resp);
    }

    private void handleSaveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        Author author = new Author();
        try {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isBlank()) {
                author.setAuthorId(Integer.parseInt(idStr));
            }
        } catch (Exception ignored) {}

        author.setAuthorName(trim(req.getParameter("authorName")));
        try {
            String dateStr = req.getParameter("dateOfBirth");
            if (dateStr != null && !dateStr.isBlank()) {
                author.setDateOfBirth(java.time.LocalDate.parse(dateStr));
            }
        } catch (Exception ignored) {}

        // Kiểm tra trùng tên tác giả cho cả add và edit
        if (author.getAuthorId() == 0) {
            if (authorService_23110327.existsByAuthorName(author.getAuthorName())) {
                req.setAttribute("alert", "Tên tác giả đã tồn tại");
                req.setAttribute("author", author);
                req.getRequestDispatcher("/views/admin/authors/addOrEdit.jsp").forward(req, resp);
                return;
            }
        } else {
            if (authorService_23110327.existsByAuthorNameExceptId(author.getAuthorName(), author.getAuthorId())) {
                req.setAttribute("alert", "Tên tác giả đã tồn tại ở tác giả khác");
                req.setAttribute("author", author);
                req.getRequestDispatcher("/views/admin/authors/addOrEdit.jsp").forward(req, resp);
                return;
            }
        }

        if (author.getAuthorId() == 0) {
            authorService_23110327.save(author);
        } else {
            authorService_23110327.update(author);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/authors");
    }

    private static int parseIntOrDefault(String s, int d) {
        try { return Integer.parseInt(s); } catch (Exception e) { return d; }
    }
    private static String trim(String s) { return s != null ? s.trim() : null; }
}
