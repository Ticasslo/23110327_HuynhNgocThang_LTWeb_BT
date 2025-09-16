<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<sitemesh:write property="head">
    <title>Đăng nhập</title>
    <style>
        html, body { width: 100%; max-width: 100%; overflow-x: hidden; }
        .login-container {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            padding: 0;
        }
        .login-card {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        .login-title {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
            font-weight: 300;
        }
        .form-floating {
            margin-bottom: 20px;
        }
        .btn-login {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            padding: 12px;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102,126,234,0.4);
        }
    </style>
</sitemesh:write>

<div class="login-container">
    <div class="login-card">
        <form action="login" method="post">
            <h2 class="login-title">
                <i class="bi bi-person-circle"></i> Đăng nhập
            </h2>
            
            <c:if test="${alert != null}">
                <div class="alert alert-danger">
                    <i class="bi bi-exclamation-triangle"></i> ${alert}
                </div>
            </c:if>

            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="username" name="username" 
                       placeholder="Tài khoản" required>
                <label for="username">
                    <i class="bi bi-person"></i> Tài khoản
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="password" class="form-control" id="password" name="password" 
                       placeholder="Mật khẩu" required>
                <label for="password">
                    <i class="bi bi-lock"></i> Mật khẩu
                </label>
            </div>

            <div class="form-check mb-3">
                <input type="checkbox" class="form-check-input" id="remember" name="remember">
                <label class="form-check-label" for="remember">
                    Ghi nhớ đăng nhập
                </label>
            </div>

            <button type="submit" class="btn btn-primary btn-login w-100">
                <i class="bi bi-box-arrow-in-right"></i> Đăng nhập
            </button>

            <div class="text-center mt-4">
                <p>
                    <a href="${pageContext.request.contextPath}/forgot-password" 
                       class="text-decoration-none">
                        <i class="bi bi-question-circle"></i> Quên mật khẩu?
                    </a>
                </p>
                <p>
                    Chưa có tài khoản? 
                    <a href="${pageContext.request.contextPath}/register" 
                       class="text-decoration-none fw-bold">
                        <i class="bi bi-person-plus"></i> Đăng ký ngay
                    </a>
                </p>
            </div>
        </form>
    </div>
</div>
