<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<sitemesh:write property="head">
    <title>Servlet CRUD MVC - Trang chủ</title>
    <style>
        .welcome-container {
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            margin: 20px 0;
        }
        .welcome-title {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            font-size: 2.5rem;
            font-weight: 300;
        }
        .menu-section {
            text-align: center;
        }
        .btn-custom {
            display: inline-block;
            padding: 15px 30px;
            margin: 10px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 500;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(0,123,255,0.3);
        }
        .btn-custom:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0,123,255,0.4);
            color: white;
        }
        .btn-admin {
            background-color: #28a745;
            box-shadow: 0 4px 15px rgba(40,167,69,0.3);
        }
        .btn-admin:hover {
            background-color: #1e7e34;
            box-shadow: 0 6px 20px rgba(40,167,69,0.4);
        }
        .description {
            margin: 30px 0;
            padding: 25px;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            border-radius: 10px;
            border-left: 4px solid #007bff;
        }
    </style>
</sitemesh:write>

<div class="welcome-container">
    <h1 class="welcome-title">🛍️ Servlet CRUD MVC Application</h1>
    
    <div class="description">
        <h4>Chào mừng đến với hệ thống quản lý!</h4>
        <p>Đây là ứng dụng web được xây dựng bằng Servlet, JSP và JPA với kiến trúc MVC. 
        Sử dụng SiteMesh Decorators để tạo layout chung cho toàn bộ ứng dụng.</p>
    </div>
    
    <div class="menu-section">
        <h3>Menu chính:</h3>
        <a href="<c:url value='/admin/home'/>" class="btn-custom btn-admin">
            <i class="bi bi-gear"></i> Admin Panel
        </a>
        <a href="<c:url value='/admin/category/list'/>" class="btn-custom">
            <i class="bi bi-list-ul"></i> Quản lý danh mục
        </a>
        <a href="<c:url value='/admin/category/add'/>" class="btn-custom">
            <i class="bi bi-plus-circle"></i> Thêm danh mục mới
        </a>
    </div>
</div>
