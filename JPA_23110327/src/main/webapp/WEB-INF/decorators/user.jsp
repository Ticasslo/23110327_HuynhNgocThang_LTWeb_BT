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
        .layout-sidebar { min-height: calc(100vh - 56px); }
        /* User blue theme */
        .sidebar-wrap { background: linear-gradient(180deg, #1d4ed8 0%, #0ea5e9 100%); }
        .sidebar-wrap .list-group-item { background: transparent; color: #ffffff; border: none; padding: 14px 18px; font-weight: 500; }
        .sidebar-wrap .list-group-item + .list-group-item { border-top: 1px solid rgba(255,255,255,.08); }
        .sidebar-wrap .list-group-item:hover { background: rgba(255,255,255,.10); color: #ffffff; }
        .sidebar-wrap .list-group-item.active { background: rgba(255,255,255,.18); color: #ffffff; }
        .user-content { background: linear-gradient(180deg,#eef7ff 0%, #e5f2ff 100%); border: 1px solid #d7ecff; border-radius: 0; border-left: none; padding: 16px; }
    </style>
    <sitemesh:write property="head"/>
</head>
<body class="d-flex flex-column min-vh-100">
    <div>
        <%@ include file="/common/user/header.jsp"%>
    </div>

    <div class="container-fluid px-0 flex-fill">
        <div class="row g-0">
            <div class="col-12 col-md-2 col-lg-2 layout-sidebar p-0 sidebar-wrap">
                <%@ include file="/common/user/left.jsp"%>
            </div>
            <div class="col-12 col-md-10 col-lg-10 p-3 user-content">
                <sitemesh:write property="body"/>
            </div>
        </div>
    </div>

    <div class="mt-auto">
        <%@ include file="/common/user/footer.jsp"%>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <sitemesh:write property="page.scripts"/>
</body>
</html>
