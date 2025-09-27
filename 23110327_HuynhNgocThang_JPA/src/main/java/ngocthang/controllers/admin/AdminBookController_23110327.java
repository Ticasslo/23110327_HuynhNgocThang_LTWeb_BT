package ngocthang.controllers.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.Book;
import ngocthang.services.IBookService_23110327;
import ngocthang.services.impl.BookServiceImpl_23110327;
import ngocthang.utils.UploadHelper_23110327;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = {
    "/admin/books",
    "/admin/books/add",
    "/admin/books/saveOrUpdate",
    "/admin/books/edit",
    "/admin/books/delete"
})
@MultipartConfig
public class AdminBookController_23110327 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IBookService_23110327 iBookService_23110327 = new BookServiceImpl_23110327();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/admin/books")) {
            handleSearchPaginated(req, resp);
            return;
        }
        if (uri.endsWith("/admin/books/add")) {
            req.setAttribute("book", new Book());
            req.getRequestDispatcher("/views/admin/books/addOrEdit.jsp").forward(req, resp);
            return;
        }
        if (uri.endsWith("/admin/books/edit")) {
            String idStr = req.getParameter("id");
            try {
                Integer id = Integer.parseInt(idStr);
                Book book = iBookService_23110327.findById(id);
                if (book != null) {
                    req.setAttribute("book", book);
                    req.getRequestDispatcher("/views/admin/books/addOrEdit.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception ignored) {}
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/admin/books/saveOrUpdate")) {
            handleSaveOrUpdate(req, resp);
            return;
        }
        if (uri.endsWith("/admin/books/delete")) {
            String idStr = req.getParameter("id");
            try {
                Integer id = Integer.parseInt(idStr);
                iBookService_23110327.deleteById(id);
            } catch (Exception ignored) {}
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        }
    }

    private void handleSearchPaginated(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        int page = parseIntOrDefault(req.getParameter("page"), 1);
        int size = parseIntOrDefault(req.getParameter("size"), 5);

        List<Book> books;
        long totalItems;
        if (keyword != null && !keyword.trim().isEmpty()) {
            books = iBookService_23110327.findByTitleOrPublisherLikePaged(keyword.trim(), page, size);
            totalItems = iBookService_23110327.countByTitleOrPublisherLike(keyword.trim());
            req.setAttribute("keyword", keyword.trim());
        } else {
            books = iBookService_23110327.findAllPaged(page, size);
            totalItems = iBookService_23110327.countAll();
        }

        long totalPages = (long) Math.ceil((double) totalItems / size);
        if (totalPages == 0) totalPages = 1;

        req.setAttribute("books", books);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/views/admin/books/searchpaginated.jsp").forward(req, resp);
    }

    private void handleSaveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        Book book = new Book();
        try {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isBlank()) {
                book.setBookId(Integer.parseInt(idStr));
            }
        } catch (Exception ignored) {}

        book.setTitle(trim(req.getParameter("title")));
        book.setPublisher(trim(req.getParameter("publisher")));
        try {
            String isbnStr = req.getParameter("isbn");
            book.setIsbn(isbnStr != null && !isbnStr.isBlank() ? Long.parseLong(isbnStr) : null);
        } catch (Exception ignored) {}
        try {
            String priceStr = req.getParameter("price");
            book.setPrice(priceStr != null && !priceStr.isBlank() ? new java.math.BigDecimal(priceStr) : null);
        } catch (Exception ignored) {}
        book.setDescription(trim(req.getParameter("description")));
        try {
            String publishDateStr = req.getParameter("publishDate");
            if (publishDateStr != null && !publishDateStr.isBlank()) {
                book.setPublishDate(java.time.LocalDate.parse(publishDateStr));
            }
        } catch (Exception ignored) {}
        
        // Xử lý upload ảnh bìa
        try {
            Part imagePart = req.getPart("coverImageFile");
            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = UploadHelper_23110327.save(imagePart);
                book.setCoverImage(fileName);
            } else {
                // Nếu không có file mới, giữ nguyên ảnh cũ
                String existingImage = trim(req.getParameter("existingCoverImage"));
                book.setCoverImage(existingImage);
            }
        } catch (Exception e) {
            // Nếu lỗi upload, giữ nguyên ảnh cũ
            String existingImage = trim(req.getParameter("existingCoverImage"));
            book.setCoverImage(existingImage);
        }
        
        try {
            String quantityStr = req.getParameter("quantity");
            book.setQuantity(quantityStr != null && !quantityStr.isBlank() ? Integer.parseInt(quantityStr) : null);
        } catch (Exception ignored) {}

        // Kiểm tra trùng ISBN cho cả add và edit
        if (book.getBookId() == 0) {
            if (book.getIsbn() != null && iBookService_23110327.existsByIsbn(book.getIsbn())) {
                req.setAttribute("alert", "ISBN đã tồn tại");
                req.setAttribute("book", book);
                req.getRequestDispatcher("/views/admin/books/addOrEdit.jsp").forward(req, resp);
                return;
            }
        } else {
            if (book.getIsbn() != null && iBookService_23110327.existsByIsbnExceptId(book.getIsbn(), book.getBookId())) {
                req.setAttribute("alert", "ISBN đã tồn tại ở sách khác");
                req.setAttribute("book", book);
                req.getRequestDispatcher("/views/admin/books/addOrEdit.jsp").forward(req, resp);
                return;
            }
        }

        if (book.getBookId() == 0) {
            iBookService_23110327.save(book);
        } else {
            iBookService_23110327.update(book);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/books");
    }

    private static int parseIntOrDefault(String s, int d) {
        try { return Integer.parseInt(s); } catch (Exception e) { return d; }
    }
    private static String trim(String s) { return s != null ? s.trim() : null; }
}
