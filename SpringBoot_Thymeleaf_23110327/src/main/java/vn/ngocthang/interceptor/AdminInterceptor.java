package vn.ngocthang.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vn.ngocthang.utils.SessionUtils;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Kiểm tra đã đăng nhập chưa
        if (!SessionUtils.isLoggedIn(request)) {
            // Lưu URL hiện tại để redirect sau khi đăng nhập
            String requestURI = request.getRequestURI();
            String contextPath = request.getContextPath();
            String redirectUrl = requestURI.substring(contextPath.length());
            SessionUtils.setRedirectUrl(request, redirectUrl);
            
            // Redirect về trang login
            response.sendRedirect(contextPath + "/login");
            return false;
        }
        
        // Kiểm tra có phải Admin không
        if (!SessionUtils.isAdmin(request)) {
            // Nếu không phải Admin, redirect về trang chủ
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }
        
        // Nếu đã đăng nhập và là Admin, cho phép tiếp tục
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Thêm thông tin user vào model nếu cần
        if (modelAndView != null) {
            var user = SessionUtils.getUser(request);
            if (user != null) {
                modelAndView.addObject("currentUser", user);
            }
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Xử lý exception nếu có
        if (ex != null) {
            System.err.println("Exception occurred in AdminInterceptor: " + ex.getMessage());
        }
    }
}
