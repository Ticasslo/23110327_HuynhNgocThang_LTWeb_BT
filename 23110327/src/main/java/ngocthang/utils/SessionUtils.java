package ngocthang.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ngocthang.models.User;

public class SessionUtils {
    
    /**
     * Lưu user vào session
     * @param request HttpServletRequest
     * @param user User object
     */
    public static void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(Constant.SESSION_ACCOUNT, user);
    }
    
    /**
     * Lấy user từ session
     * @param request HttpServletRequest
     * @return User object hoặc null nếu chưa đăng nhập
     */
    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute(Constant.SESSION_ACCOUNT);
        }
        return null;
    }
    
    /**
     * Kiểm tra user đã đăng nhập chưa
     * @param request HttpServletRequest
     * @return true nếu đã đăng nhập, false nếu chưa
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getUser(request) != null;
    }
    
    /**
     * Kiểm tra user có phải Admin không
     * @param request HttpServletRequest
     * @return true nếu là Admin, false nếu không
     */
    public static boolean isAdmin(HttpServletRequest request) {
        User user = getUser(request);
        return user != null && user.getRoleid() == Constant.ROLE_ADMIN;
    }
    
    /**
     * Kiểm tra user có phải Manager không
     * @param request HttpServletRequest
     * @return true nếu là Manager, false nếu không
     */
    public static boolean isManager(HttpServletRequest request) {
        User user = getUser(request);
        return user != null && user.getRoleid() == Constant.ROLE_MANAGER;
    }
    
    /**
     * Kiểm tra user có phải Seller không
     * @param request HttpServletRequest
     * @return true nếu là Seller, false nếu không
     */
    public static boolean isSeller(HttpServletRequest request) {
        User user = getUser(request);
        return user != null && user.getRoleid() == Constant.ROLE_SELLER;
    }
    
    /**
     * Lưu URL redirect vào session (dùng cho filter)
     * @param request HttpServletRequest
     * @param url URL cần redirect sau khi đăng nhập
     */
    public static void setRedirectUrl(HttpServletRequest request, String url) {
        HttpSession session = request.getSession(true);
        session.setAttribute("redirectUrl", url);
    }
    
    /**
     * Lấy URL redirect từ session
     * @param request HttpServletRequest
     * @return URL redirect hoặc null nếu không có
     */
    public static String getRedirectUrl(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute("redirectUrl");
        }
        return null;
    }
    
    /**
     * Xóa URL redirect khỏi session
     * @param request HttpServletRequest
     */
    public static void removeRedirectUrl(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("redirectUrl");
        }
    }
    
    /**
     * Xóa toàn bộ session (đăng xuất)
     * @param request HttpServletRequest
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
} 