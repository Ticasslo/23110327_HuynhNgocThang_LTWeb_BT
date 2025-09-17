package vn.ngocthang.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtils {
    
    private static final String COOKIE_REMEMBER = "username";
    
    /**
     * Lấy username từ Remember Me cookie
     * @param request HttpServletRequest
     * @return username hoặc null nếu không có
     */
    public static String getRememberMeUsername(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_REMEMBER.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    /**
     * Tạo Remember Me cookie
     * @param response HttpServletResponse
     * @param username username để nhớ
     */
    public static void saveRememberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(COOKIE_REMEMBER, username);
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
        cookie.setPath("/");
        cookie.setHttpOnly(true); // Bảo mật - không cho JavaScript truy cập
        response.addCookie(cookie);
    }
    
    /**
     * Xóa Remember Me cookie
     * @param response HttpServletResponse
     */
    public static void deleteRememberMe(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_REMEMBER, "");
        cookie.setMaxAge(0); // Xóa cookie
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
