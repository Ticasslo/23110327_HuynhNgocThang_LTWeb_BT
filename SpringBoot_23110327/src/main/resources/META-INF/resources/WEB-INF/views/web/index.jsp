<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<sitemesh:write property="head">
<title>Trang chủ - Video Manager</title>
<style>
    .hero-section {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 80px 0;
    }
    .feature-card {
        transition: transform 0.3s ease;
    }
    .feature-card:hover {
        transform: translateY(-5px);
    }
</style>
</sitemesh:write>

<!-- Hero Section -->
<div class="hero-section">
    <div class="container text-center">
        <h1 class="display-4 fw-bold mb-4">
            <i class="bi bi-play-circle"></i> Video Manager System
        </h1>
        <p class="lead mb-4">Quản lý video chuyên nghiệp, dễ dàng và hiệu quả</p>
        <c:choose>
            <c:when test="${not empty sessionScope.account}">
                <a href="/waiting" class="btn btn-light btn-lg">
                    <i class="bi bi-arrow-right"></i> Vào hệ thống
                </a>
            </c:when>
            <c:otherwise>
                <a href="/login" class="btn btn-light btn-lg me-3">
                    <i class="bi bi-box-arrow-in-right"></i> Đăng nhập
                </a>
                <a href="/register" class="btn btn-outline-light btn-lg">
                    <i class="bi bi-person-plus"></i> Đăng ký
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- Features Section -->
<div class="container my-5">
    <div class="row">
        <div class="col-lg-4 mb-4">
            <div class="card feature-card h-100 text-center">
                <div class="card-body">
                    <i class="bi bi-collection-play display-4 text-primary mb-3"></i>
                    <h5 class="card-title">Quản lý Video</h5>
                    <p class="card-text">Upload, tổ chức và quản lý video một cách dễ dàng với giao diện thân thiện.</p>
                </div>
            </div>
        </div>
        <div class="col-lg-4 mb-4">
            <div class="card feature-card h-100 text-center">
                <div class="card-body">
                    <i class="bi bi-folder2-open display-4 text-success mb-3"></i>
                    <h5 class="card-title">Danh mục</h5>
                    <p class="card-text">Phân loại video theo danh mục để dễ dàng tìm kiếm và quản lý.</p>
                </div>
            </div>
        </div>
        <div class="col-lg-4 mb-4">
            <div class="card feature-card h-100 text-center">
                <div class="card-body">
                    <i class="bi bi-people display-4 text-warning mb-3"></i>
                    <h5 class="card-title">Quản lý User</h5>
                    <p class="card-text">Hệ thống phân quyền Admin/User với các chức năng quản lý phù hợp.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Stats Section -->
<div class="bg-light py-5">
    <div class="container">
        <div class="row text-center">
            <div class="col-md-3 mb-3">
                <h3 class="text-primary">1000+</h3>
                <p class="text-muted">Video được quản lý</p>
            </div>
            <div class="col-md-3 mb-3">
                <h3 class="text-success">50+</h3>
                <p class="text-muted">Danh mục</p>
            </div>
            <div class="col-md-3 mb-3">
                <h3 class="text-warning">500+</h3>
                <p class="text-muted">Người dùng</p>
            </div>
            <div class="col-md-3 mb-3">
                <h3 class="text-info">99%</h3>
                <p class="text-muted">Hài lòng</p>
            </div>
        </div>
    </div>
</div>