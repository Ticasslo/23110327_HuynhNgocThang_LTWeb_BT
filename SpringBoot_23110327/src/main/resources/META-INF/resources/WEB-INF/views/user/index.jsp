<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<sitemesh:write property="head">
<title>User Dashboard - Video Manager</title>
<style>
    .stats-card {
        transition: transform 0.3s ease;
    }
    .stats-card:hover {
        transform: translateY(-5px);
    }
</style>
</sitemesh:write>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h1><i class="bi bi-person-circle"></i> User Dashboard</h1>
    <span class="badge bg-primary fs-6">Chào mừng, ${sessionScope.account.fullName}!</span>
</div>

<!-- User Stats -->
<div class="row mb-4">
    <div class="col-md-4 mb-3">
        <div class="card stats-card bg-primary text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>45</h4>
                        <p class="mb-0">Video của tôi</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-collection-play fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 mb-3">
        <div class="card stats-card bg-success text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>2.3K</h4>
                        <p class="mb-0">Lượt xem</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-eye fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 mb-3">
        <div class="card stats-card bg-warning text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>12</h4>
                        <p class="mb-0">Danh mục</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-folder2-open fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Quick Actions -->
<div class="row">
    <div class="col-md-6">
        <div class="card">
            <div class="card-header">
                <h5><i class="bi bi-lightning"></i> Thao tác nhanh</h5>
            </div>
            <div class="card-body">
                <div class="d-grid gap-2">
                    <a href="/user/videos" class="btn btn-outline-primary">
                        <i class="bi bi-collection-play"></i> Video của tôi
                    </a>
                    <a href="/user/categories" class="btn btn-outline-success">
                        <i class="bi bi-folder2-open"></i> Xem danh mục
                    </a>
                    <a href="/user/profile" class="btn btn-outline-info">
                        <i class="bi bi-person-gear"></i> Thông tin cá nhân
                    </a>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-header">
                <h5><i class="bi bi-clock-history"></i> Hoạt động gần đây</h5>
            </div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Video mới nhất</span>
                        <small class="text-muted">2 giờ trước</small>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Lượt xem hôm nay</span>
                        <span class="badge bg-primary">156</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Danh mục yêu thích</span>
                        <span class="badge bg-success">Giáo dục</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- Recent Videos -->
<div class="row mt-4">
    <div class="col-12">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5><i class="bi bi-collection-play"></i> Video gần đây</h5>
                <a href="/user/videos" class="btn btn-sm btn-outline-primary">Xem tất cả</a>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 150px;">
                                <i class="bi bi-play-circle fs-1 text-muted"></i>
                            </div>
                            <div class="card-body p-2">
                                <h6 class="card-title">Video mẫu 1</h6>
                                <small class="text-muted">156 lượt xem</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 150px;">
                                <i class="bi bi-play-circle fs-1 text-muted"></i>
                            </div>
                            <div class="card-body p-2">
                                <h6 class="card-title">Video mẫu 2</h6>
                                <small class="text-muted">89 lượt xem</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 150px;">
                                <i class="bi bi-play-circle fs-1 text-muted"></i>
                            </div>
                            <div class="card-body p-2">
                                <h6 class="card-title">Video mẫu 3</h6>
                                <small class="text-muted">234 lượt xem</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-img-top bg-light d-flex align-items-center justify-content-center" style="height: 150px;">
                                <i class="bi bi-play-circle fs-1 text-muted"></i>
                            </div>
                            <div class="card-body p-2">
                                <h6 class="card-title">Video mẫu 4</h6>
                                <small class="text-muted">67 lượt xem</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
