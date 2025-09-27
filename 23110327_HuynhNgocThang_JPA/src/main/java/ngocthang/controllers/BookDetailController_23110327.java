package ngocthang.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.entity.Book;
import ngocthang.entity.Rating;
import ngocthang.entity.User;
import ngocthang.services.IBookService_23110327;
import ngocthang.services.IRatingService_23110327;
import ngocthang.services.impl.BookServiceImpl_23110327;
import ngocthang.services.impl.RatingServiceImpl_23110327;
import ngocthang.utils.SessionUtils_23110327;

@WebServlet(urlPatterns = {"/bookdetail"})
public class BookDetailController_23110327 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IBookService_23110327 iBookService_23110327 = new BookServiceImpl_23110327();
    private final IRatingService_23110327 ratingService_23110327 = new RatingServiceImpl_23110327();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra đăng nhập
        if (!SessionUtils_23110327.isLoggedIn(req)) {
            String redirectUrl = req.getRequestURI().substring(req.getContextPath().length()) + "?" + req.getQueryString();
            SessionUtils_23110327.setRedirectUrl(req, redirectUrl);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String bookIdStr = req.getParameter("id");
        if (bookIdStr == null || bookIdStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/booklist");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdStr);
            Book book = iBookService_23110327.findById(bookId);
            if (book == null) {
                resp.sendRedirect(req.getContextPath() + "/booklist");
                return;
            }

            List<Object[]> bookData = iBookService_23110327.findBookWithAuthorsAndReviews(bookId);
            req.setAttribute("book", book);
            req.setAttribute("bookData", bookData);

            req.getRequestDispatcher("/views/books/detail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/booklist");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra đăng nhập
        if (!SessionUtils_23110327.isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        
        String bookIdStr = req.getParameter("id");
        String ratingStr = req.getParameter("rating");
        String reviewText = req.getParameter("reviewText");

        if (bookIdStr == null || bookIdStr.trim().isEmpty() || 
            ratingStr == null || ratingStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/booklist");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdStr);
            Byte rating = Byte.parseByte(ratingStr);
            User currentUser = SessionUtils_23110327.getUser(req);
            
            // Tạo hoặc cập nhật rating
            Rating existingRating = ratingService_23110327.findByUserIdAndBookId(currentUser.getId(), bookId);
            
            if (existingRating != null) {
                // Cập nhật rating đã tồn tại
                existingRating.setRating(rating);
                existingRating.setReviewText(reviewText != null ? reviewText.trim() : null);
                ratingService_23110327.update(existingRating);
            } else {
                // Tạo rating mới
                Rating newRating = new Rating();
                newRating.setId(new Rating.RatingId(currentUser.getId(), bookId));
                newRating.setRating(rating);
                newRating.setReviewText(reviewText != null ? reviewText.trim() : null);
                ratingService_23110327.save(newRating);
            }
            
            // Redirect về trang chi tiết sau khi submit
            resp.sendRedirect(req.getContextPath() + "/bookdetail?id=" + bookId);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/booklist");
        }
    }
}
