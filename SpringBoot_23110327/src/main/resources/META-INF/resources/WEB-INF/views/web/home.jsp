<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
    .feature-card {
        transition: transform 0.3s ease;
    }
    .feature-card:hover {
        transform: translateY(-5px);
    }
    .video-card {
        transition: transform 0.3s ease;
        cursor: pointer;
    }
    .video-card:hover {
        transform: translateY(-5px);
    }
    .video-thumbnail {
        height: 200px;
        background: linear-gradient(45deg, #667eea 0%, #764ba2 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 3rem;
    }
    .hero-section {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 80px 0;
    }
</style>

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

<!-- Simple Welcome Message -->
<div class="container my-5">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body text-center py-5">
                    <i class="bi bi-play-circle display-1 text-primary mb-3"></i>
                    <h3>Chào mừng đến với Video Manager System</h3>
                    <p class="text-muted">Hệ thống quản lý video chuyên nghiệp và dễ sử dụng</p>
                </div>
            </div>
        </div>
    </div>
</div>