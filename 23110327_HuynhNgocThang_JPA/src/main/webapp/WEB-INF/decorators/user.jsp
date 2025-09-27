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

    <sitemesh:write property="head"/>
</head>
<body class="d-flex flex-column min-vh-100">
    <div>
        <%@ include file="/common/user/header.jsp"%>
    </div>

    <div class="container-fluid px-0 flex-fill">
        <div class="row g-0 min-vh-100">
            <div class="col-12 col-md-2 col-lg-2 p-0 bg-secondary-subtle border-end min-vh-100">
                <%@ include file="/common/user/left.jsp"%>
            </div>

            <div class="col-12 col-md-10 col-lg-10 p-3 min-vh-100">
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
