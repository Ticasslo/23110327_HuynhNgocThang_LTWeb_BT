<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
    .stats-card {
        transition: transform 0.3s ease;
    }
    .stats-card:hover {
        transform: translateY(-5px);
    }
    .admin-welcome {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 40px 0;
        border-radius: 10px;
        margin-bottom: 30px;
    }
</style>

<div class="container-fluid">
    <!-- Welcome Section -->
    <div class="admin-welcome">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="display-4 fw-bold mb-3">
                    <i class="bi bi-shield-check"></i> Chào mừng Admin!
                </h1>
                <p class="lead mb-0">Quản lý hệ thống Video Manager một cách hiệu quả</p>
            </div>
        </div>
    </div>

    <!-- Stats Cards -->
    <div class="row mb-4">
        <div class="col-md-3 mb-3">
            <div class="card stats-card bg-primary text-white">
                <div class="card-body text-center">
                    <i class="bi bi-collection-play display-4 mb-2"></i>
                    <h3>${totalVideos}</h3>
                    <p class="mb-0">Tổng Video</p>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card stats-card bg-success text-white">
                <div class="card-body text-center">
                    <i class="bi bi-folder2-open display-4 mb-2"></i>
                    <h3>${totalCategories}</h3>
                    <p class="mb-0">Danh mục</p>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card stats-card bg-warning text-white">
                <div class="card-body text-center">
                    <i class="bi bi-people display-4 mb-2"></i>
                    <h3>${totalUsers}</h3>
                    <p class="mb-0">Người dùng</p>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-3">
            <div class="card stats-card bg-info text-white">
                <div class="card-body text-center">
                    <i class="bi bi-eye display-4 mb-2"></i>
                    <h3>${totalViews}</h3>
                    <p class="mb-0">Lượt xem</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Simple Welcome Message -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body text-center py-5">
                    <i class="bi bi-shield-check display-1 text-primary mb-3"></i>
                    <h3>Chào mừng đến với Admin Panel</h3>
                    <p class="text-muted">Sử dụng menu bên trái để quản lý hệ thống</p>
                </div>
            </div>
        </div>
    </div>
</div>