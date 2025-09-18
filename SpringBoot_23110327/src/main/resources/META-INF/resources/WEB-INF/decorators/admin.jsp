<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title"/></title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        html, body { width: 100%; max-width: 100%; overflow-x: hidden; }
        .layout-sidebar { 
            min-height: calc(100vh - 56px); 
            background: linear-gradient(180deg, #673ab7 0%, #8e24aa 100%);
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
            border-left: 4px solid #ffc107;
        }
        .admin-content {
            background: linear-gradient(180deg,#f5f3ff 0%, #eee9ff 100%); 
            border: 1px solid #e5defd; 
            border-radius: 0;
            border-left: none;
            padding: 16px;
        }
    </style>
    <sitemesh:write property="head"/>
</head>
<body class="d-flex flex-column min-vh-100">
    <!-- Admin Header -->
    <div>
        <%@ include file="/common/admin/header.jsp"%>
    </div>
    
    <div class="container-fluid px-0 flex-fill">
        <div class="row g-0">
            <div class="col-12 col-md-2 col-lg-2 layout-sidebar p-0 sidebar-wrap">
                <%@ include file="/common/admin/left.jsp"%>
            </div>
            <div class="col-12 col-md-10 col-lg-10 p-3 admin-content">
                <sitemesh:write property="body"/>
            </div>
        </div>
    </div>
    
    <div>
        <%@ include file="/common/admin/footer.jsp"%>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>