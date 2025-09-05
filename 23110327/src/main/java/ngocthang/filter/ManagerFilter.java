package ngocthang.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ngocthang.utils.SessionUtils;

@WebFilter(urlPatterns = {"/manager/*"})
public class ManagerFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (!SessionUtils.isLoggedIn(req)) {
            // Lưu URL hiện tại để redirect sau khi đăng nhập
            String currentUrl = req.getRequestURI();
            if (req.getQueryString() != null) {
                currentUrl += "?" + req.getQueryString();
            }
            SessionUtils.setRedirectUrl(req, currentUrl.substring(req.getContextPath().length()));
            
            // Chuyển hướng đến trang đăng nhập
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        // Kiểm tra quyền Manager (chỉ Manager mới được truy cập)
        if (!SessionUtils.isManager(req)) {
            // Không có quyền Manager - chuyển hướng về waiting để phân tích role
            resp.sendRedirect(req.getContextPath() + "/waiting");
            return;
        }
        
        // Có quyền Manager - tiếp tục xử lý request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Dọn dẹp tài nguyên
    }
}
