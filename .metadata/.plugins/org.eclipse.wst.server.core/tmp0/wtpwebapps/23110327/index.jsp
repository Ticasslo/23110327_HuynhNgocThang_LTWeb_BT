<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Servlet CRUD MVC - Trang chủ</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .menu {
            text-align: center;
        }
        .btn {
            display: inline-block;
            padding: 12px 25px;
            margin: 10px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .btn-admin {
            background-color: #28a745;
        }
        .btn-admin:hover {
            background-color: #1e7e34;
        }
        .description {
            margin: 20px 0;
            padding: 20px;
            background-color: #e9ecef;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🛍️ Servlet CRUD MVC Application</h1>
        
        <div class="menu">
            <h3>Menu:</h3>
            <a href="<c:url value='/admin/home'/>" class="btn btn-admin">🎛️ Admin Panel</a>
            <a href="<c:url value='/admin/category/list'/>" class="btn">📋 Quản lý danh mục</a>
            <a href="<c:url value='/admin/category/add'/>" class="btn">➕ Thêm danh mục mới</a>
        </div>
    </div>
</body>
</html>