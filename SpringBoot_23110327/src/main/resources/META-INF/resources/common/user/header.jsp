<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-success" style="z-index:1100;">
    <div class="container-fluid">
        <a class="navbar-brand" href="/user">
            <i class="bi bi-person-circle"></i> User Panel
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/user">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/videos">Video của tôi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/search">Tìm kiếm</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" data-bs-display="static">
                        <i class="bi bi-person-circle"></i> ${sessionScope.account.fullName}
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="/user/profile">Thông tin cá nhân</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="/logout">Đăng xuất</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<style>
    .dropdown-menu { min-width: 220px; }
    .navbar .nav-link { white-space: nowrap; }
</style>