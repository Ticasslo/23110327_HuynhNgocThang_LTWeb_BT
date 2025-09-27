package vn.ngocthang.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtils {
    
    private static final String REMEMBER_ME_COOKIE = "rememberMe";
    private static final int COOKIE_MAX_AGE = 7 * 24 * 60 * 60; // 7 days
    
    /**
     * Lưu thông tin remember me vào cookie
     * @param response HttpServletResponse
     * @param username Tên đăng nhập
     */
    public static void saveRememberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(REMEMBER_ME_COOKIE, username);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * Lấy username từ remember me cookie
     * @param request HttpServletRequest
     * @return Username hoặc null nếu không có cookie
     */
    public static String getRememberMeUsername(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REMEMBER_ME_COOKIE.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    /**
     * Xóa remember me cookie
     * @param response HttpServletResponse
     */
    public static void clearRememberMe(HttpServletResponse response) {
        Cookie cookie = new Cookie(REMEMBER_ME_COOKIE, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
