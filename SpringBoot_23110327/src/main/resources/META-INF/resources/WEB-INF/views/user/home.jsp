<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
    .stats-card {
        transition: transform 0.3s ease;
    }
    .stats-card:hover {
        transform: translateY(-5px);
    }
    .user-welcome {
        background: linear-gradient(135deg, #1d4ed8 0%, #0ea5e9 100%);
        color: white;
        padding: 40px 0;
        border-radius: 10px;
        margin-bottom: 30px;
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
        background: linear-gradient(45deg, #1d4ed8 0%, #0ea5e9 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 3rem;
    }
</style>

<div class="container-fluid">
    <!-- Welcome Section -->
    <div class="user-welcome">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="display-4 fw-bold mb-3">
                    <i class="bi bi-person-circle"></i> Chào mừng ${sessionScope.account.fullName}!
                </h1>
                <p class="lead mb-0">Khám phá và quản lý video của bạn</p>
            </div>
        </div>
    </div>

    <!-- Simple Welcome Message -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body text-center py-5">
                    <i class="bi bi-person-circle display-1 text-primary mb-3"></i>
                    <h3>Chào mừng đến với User Panel</h3>
                    <p class="text-muted">Sử dụng menu bên trái để quản lý video của bạn</p>
                </div>
            </div>
        </div>
    </div>
</div>
