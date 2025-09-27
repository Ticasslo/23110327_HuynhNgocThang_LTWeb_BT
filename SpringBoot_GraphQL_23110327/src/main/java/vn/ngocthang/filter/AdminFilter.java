package vn.ngocthang.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ngocthang.utils.SessionUtils;

import java.io.IOException;

public class AdminFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Kiểm tra đã đăng nhập chưa
        if (!SessionUtils.isLoggedIn(httpRequest)) {
            // Lưu URL hiện tại để redirect sau khi đăng nhập
            String requestURI = httpRequest.getRequestURI();
            String contextPath = httpRequest.getContextPath();
            String redirectUrl = requestURI.substring(contextPath.length());
            SessionUtils.setRedirectUrl(httpRequest, redirectUrl);
            
            // Redirect về trang login
            httpResponse.sendRedirect(contextPath + "/login");
            return;
        }
        
        // Kiểm tra có phải Admin không
        if (!SessionUtils.isAdmin(httpRequest)) {
            // Nếu không phải Admin, redirect về trang chủ
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
            return;
        }
        
        // Nếu đã đăng nhập và là Admin, cho phép tiếp tục
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Cleanup filter
    }
}
