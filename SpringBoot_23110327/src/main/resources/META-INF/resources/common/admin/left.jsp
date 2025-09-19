<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="position-sticky pt-3">
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link ${fn:endsWith(pageContext.request.requestURI, '/admin') ? 'active' : ''}" href="/admin">
                <i class="bi bi-house"></i> Trang chủ
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${fn:endsWith(pageContext.request.requestURI, '/admin/categories') ? 'active' : ''}" href="/admin/categories">
                <i class="bi bi-folder2-open"></i> Danh mục
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${fn:endsWith(pageContext.request.requestURI, '/admin/videos') || fn:startsWith(pageContext.request.requestURI, '/admin/videos/') ? 'active' : ''}" href="/admin/videos">
                <i class="bi bi-collection-play"></i> Video
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${fn:endsWith(pageContext.request.requestURI, '/admin/users') || fn:startsWith(pageContext.request.requestURI, '/admin/users/') ? 'active' : ''}" href="/admin/users">
                <i class="bi bi-people"></i> Người dùng
            </a>
        </li>
        
    </ul>
    
    <style>
        .nav .nav-link { color: #ffffff; }
        .nav .nav-link.active { font-weight: bold; }
    </style>
</div>