<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Home</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #ff9a56 0%, #ffad56 100%);
            min-height: 100vh;
        }
        
        .header {
            background: linear-gradient(135deg, #e67e22 0%, #f39c12 100%);
            color: white;
            padding: 20px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        }
        
        .header h1 {
            font-size: 28px;
            font-weight: 300;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .user-info {
            text-align: right;
        }
        
        .user-name {
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 5px;
        }
        
        .user-role {
            font-size: 14px;
            color: #fdeaa7;
            margin-bottom: 10px;
        }
        
        .logout-btn {
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 25px;
            font-size: 14px;
            font-weight: 500;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }
        
        .logout-btn:hover {
            background: linear-gradient(135deg, #c0392b 0%, #a93226 100%);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(231,76,60,0.3);
        }
        
        .container {
            display: flex;
            min-height: calc(100vh - 80px);
        }
        
        .sidebar {
            width: 280px;
            background: white;
            box-shadow: 4px 0 20px rgba(0,0,0,0.1);
            backdrop-filter: blur(10px);
        }
        
        .sidebar-header {
            padding: 25px 20px;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            border-bottom: 2px solid #dee2e6;
        }
        
        .sidebar h3 {
            color: #495057;
            font-size: 18px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .menu-section {
            padding: 20px 0;
        }
        
        .menu-item {
            display: block;
            padding: 15px 25px;
            color: #495057;
            text-decoration: none;
            transition: all 0.3s ease;
            border-left: 4px solid transparent;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 12px;
        }
        
        .menu-item:hover {
            background: linear-gradient(90deg, #fff3e0 0%, #ffe0b2 100%);
            color: #e65100;
            border-left-color: #ff9800;
            transform: translateX(5px);
        }
        
        .menu-item.active {
            background: linear-gradient(90deg, #e65100 0%, #ef6c00 100%);
            color: white;
            border-left-color: #bf360c;
        }
        
        .content {
            flex: 1;
            padding: 40px;
        }
        
        .welcome-card {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            text-align: center;
            position: relative;
            overflow: hidden;
        }
        
        .welcome-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #ff9a56 0%, #ffad56 100%);
        }
        
        .welcome-card h2 {
            color: #333;
            margin-bottom: 20px;
            font-size: 2.2em;
            font-weight: 300;
        }
        
        .welcome-card p {
            color: #6c757d;
            line-height: 1.8;
            font-size: 16px;
            margin-bottom: 15px;
        }
        
        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        
        .feature-item {
            padding: 20px;
            background: linear-gradient(135deg, #fff3e0 0%, #ffe0b2 100%);
            border-radius: 10px;
            text-align: center;
            transition: all 0.3s ease;
        }
        
        .feature-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }
        
        .feature-icon {
            font-size: 2.5em;
            margin-bottom: 15px;
            display: block;
        }
        
        .feature-title {
            color: #e65100;
            font-weight: 600;
            margin-bottom: 10px;
        }
        
        .feature-desc {
            color: #6c757d;
            font-size: 14px;
        }
        
        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                text-align: center;
                gap: 15px;
            }
            
            .container {
                flex-direction: column;
            }
            
            .sidebar {
                width: 100%;
                order: 2;
            }
            
            .content {
                order: 1;
                padding: 20px;
            }
            
            .features-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <!-- Kiểm tra đăng nhập -->
    <c:if test="${empty sessionScope.account}">
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:if>

    <!-- Header -->
    <div class="header">
        <h1>👤 <strong>User Panel</strong></h1>
        <div class="user-info">
            <div class="user-name">👤 ${sessionScope.account.fullName}</div>
            <div class="user-role">@${sessionScope.account.userName} • User</div>
            <a href="${pageContext.request.contextPath}/logout" 
               class="logout-btn"
               onclick="return confirm('🚪 Bạn có chắc chắn muốn đăng xuất?')">
                🚪 Đăng xuất
            </a>
        </div>
    </div>

    <!-- Main Container -->
    <div class="container">
        <!-- Sidebar Menu -->
        <div class="sidebar">
            <div class="sidebar-header">
                <h3>👤 Tài khoản cá nhân</h3>
            </div>
            <div class="menu-section">
                <a href="<c:url value='/user/category/list'/>" class="menu-item">
                    📋 Xem danh mục
                </a>
            </div>
        </div>

        <!-- Main Content -->
        <div class="content">
            <div class="welcome-card">
                <h2>🎉 Chào mừng đến với User Panel</h2>
                <p>Xem các danh mục sản phẩm và quản lý tài khoản cá nhân.</p>
                
                <!-- Hiển thị danh sách tất cả categories -->
                <div style="margin-top: 30px;">
                    <h3 style="color: #e65100; margin-bottom: 20px;">📂 Tất cả danh mục:</h3>
                    <div id="categoryList">
                        <!-- Categories sẽ được load ở đây -->
                        <p style="color: #6c757d; font-style: italic;">Đang tải danh sách danh mục...</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 