<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="position-sticky pt-3">
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link ${fn:endsWith(pageContext.request.requestURI, '/user') ? 'active' : ''}" href="/user">
                <i class="bi bi-house"></i> Trang chủ
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${fn:endsWith(pageContext.request.requestURI, '/user/search') ? 'active' : ''}" href="/user/search">
                <i class="bi bi-search"></i> Tìm kiếm
            </a>
        </li>
    </ul>
    <style>
        .nav .nav-link { color: #ffffff; }
        .nav .nav-link.active { font-weight: bold; }
    </style>
</div>