package vn.ngocthang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBindException(BindException ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Lấy lỗi validation đầu tiên
        String errorMessage = "Dữ liệu không hợp lệ";
        if (ex.getBindingResult().hasErrors()) {
            errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        
        // Xác định view dựa trên request URI
        String viewName = determineViewName(request.getRequestURI());
        modelAndView.setViewName(viewName);
        modelAndView.addObject("alert", errorMessage);
        
        
        return modelAndView;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Lấy lỗi validation đầu tiên
        String errorMessage = "Dữ liệu không hợp lệ";
        if (ex.getBindingResult().hasErrors()) {
            errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        
        // Xác định view dựa trên request URI
        String viewName = determineViewName(request.getRequestURI());
        modelAndView.setViewName(viewName);
        modelAndView.addObject("alert", errorMessage);
        
        
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Log lỗi (có thể thêm logger sau)
        System.err.println("Unexpected error: " + ex.getMessage());
        
        // Xác định view dựa trên request URI
        String viewName = determineViewName(request.getRequestURI());
        modelAndView.setViewName(viewName);
        modelAndView.addObject("alert", "Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau.");
        
        
        return modelAndView;
    }
    
    private String determineViewName(String requestURI) {
        if (requestURI.contains("/admin")) {
            return "admin/layout";
        } else if (requestURI.contains("/user")) {
            return "user/layout";
        } else if (requestURI.contains("/login")) {
            return "web/login";
        } else if (requestURI.contains("/register")) {
            return "web/register";
        } else {
            return "web/layout";
        }
    }
}
