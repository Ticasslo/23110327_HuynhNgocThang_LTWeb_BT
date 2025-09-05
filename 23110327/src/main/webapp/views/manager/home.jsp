<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Home</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #56ab2f 0%, #a8e6cf 100%);
            min-height: 100vh;
        }
        
        .header {
            background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
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
            color: #d5f4e6;
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
            background: linear-gradient(90deg, #e8f5e8 0%, #c8e6c9 100%);
            color: #2e7d32;
            border-left-color: #4caf50;
            transform: translateX(5px);
        }
        
        .menu-item.active {
            background: linear-gradient(90deg, #2e7d32 0%, #388e3c 100%);
            color: white;
            border-left-color: #1b5e20;
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
            background: linear-gradient(90deg, #56ab2f 0%, #a8e6cf 100%);
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
            background: linear-gradient(135deg, #f1f8e9 0%, #e8f5e8 100%);
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
            color: #2e7d32;
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
        <h1>🏢 <strong>Manager Panel</strong></h1>
        <div class="user-info">
            <div class="user-name">👤 ${sessionScope.account.fullName}</div>
            <div class="user-role">@${sessionScope.account.userName} • Manager</div>
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
                <h3>📊 Quản lý hệ thống</h3>
            </div>
            <div class="menu-section">
                <a href="#" class="menu-item">
                    📈 Báo cáo thống kê
                </a>
                <a href="#" class="menu-item">
                    👥 Quản lý nhân viên
                </a>
                <a href="#" class="menu-item">
                    🎯 Quản lý dự án
                </a>
                <a href="#" class="menu-item">
                    ⚙️ Cài đặt hệ thống
                </a>
            </div>
        </div>

        <!-- Main Content -->
        <div class="content">
            <div class="welcome-card">
                <h2>🎉 Chào mừng đến với Manager Panel</h2>
                <p>Quản lý hiệu quả các hoạt động và nhân sự trong hệ thống.</p>
                
                <div class="features-grid">
                    <div class="feature-item">
                        <span class="feature-icon">📈</span>
                        <div class="feature-title">Báo cáo</div>
                        <div class="feature-desc">Xem báo cáo chi tiết về hoạt động</div>
                    </div>
                    <div class="feature-item">
                        <span class="feature-icon">👥</span>
                        <div class="feature-title">Nhân sự</div>
                        <div class="feature-desc">Quản lý thông tin nhân viên</div>
                    </div>
                    <div class="feature-item">
                        <span class="feature-icon">🎯</span>
                        <div class="feature-title">Dự án</div>
                        <div class="feature-desc">Theo dõi tiến độ dự án</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>