<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title"/> - User Panel</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        html, body { width: 100%; max-width: 100%; overflow-x: hidden; }
        .layout-sidebar { 
            min-height: calc(100vh - 56px); 
            background: linear-gradient(180deg, #1d4ed8 0%, #0ea5e9 100%);
            color: white;
        }
        .sidebar-wrap .list-group-item {
            color: white;
            background-color: transparent;
            border: none;
            transition: all 0.3s ease;
        }
        .sidebar-wrap .list-group-item:hover {
            background-color: rgba(255, 255, 255, 0.1);
            transform: translateX(3px);
        }
        .sidebar-wrap .list-group-item.active {
            background-color: rgba(255, 255, 255, 0.2);
            font-weight: bold;
            border-left: 4px solid #93c5fd;
        }
        .user-content {
            background: linear-gradient(180deg,#eef7ff 0%, #e5f2ff 100%);
            border: 1px solid #d0e0ff; 
            border-radius: 0;
            border-left: none;
            padding: 16px;
        }
    </style>
    <sitemesh:write property="head"/>
</head>
<body class="d-flex flex-column min-vh-100">
    <!-- User Header -->
    <nav class="navbar navbar-expand-lg navbar-dark" style="background: linear-gradient(90deg,#1d4ed8 0%, #0ea5e9 100%);">
        <div class="container-fluid">
            <a class="navbar-brand" href="/user">
                <i class="bi bi-person-circle"></i> User Panel
            </a>
            <div class="navbar-nav ms-auto">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <li class="nav-item d-flex align-items-center me-3">
                            <c:choose>
                                <c:when test="${not empty sessionScope.account.avatar}">
                                    <img src="/image?fname=${sessionScope.account.avatar}" alt="Avatar" class="rounded-circle me-2" style="width: 32px; height: 32px; object-fit: cover;">
                                </c:when>
                                <c:otherwise>
                                    <i class="bi bi-person-circle me-2" style="font-size: 24px;"></i>
                                </c:otherwise>
                            </c:choose>
                            <span class="navbar-text">${sessionScope.account.fullName}</span>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-outline-light" href="/logout" onclick="return confirm('Đăng xuất?')">Đăng xuất</a>
                        </li>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </nav>
    
    <div class="container-fluid px-0 flex-fill">
        <div class="row g-0">
            <div class="col-12 col-md-2 col-lg-2 layout-sidebar p-0 sidebar-wrap">
                <div class="list-group list-group-flush">
                    <a href="/user" class="list-group-item list-group-item-action">🏠 Trang chủ</a>
                    <a href="/user/videos" class="list-group-item list-group-item-action">🎬 Video của tôi</a>
                    <a href="/user/categories" class="list-group-item list-group-item-action">📂 Danh mục</a>
                    <a href="/user/profile" class="list-group-item list-group-item-action">👤 Thông tin cá nhân</a>
                </div>
            </div>
            <div class="col-12 col-md-10 col-lg-10 p-3 user-content">
                <sitemesh:write property="body"/>
            </div>
        </div>
    </div>
    
    <footer class="text-light text-center py-3 mt-auto" style="background: linear-gradient(90deg,#1d4ed8 0%, #0ea5e9 100%);">
        <small>User Area - Video Manager System</small>
    </footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <sitemesh:write property="page.scripts"/>
</body>
</html>
