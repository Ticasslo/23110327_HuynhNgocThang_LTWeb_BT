<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<sitemesh:write property="head">
<title>Admin Dashboard - Video Manager</title>
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
    <h1><i class="bi bi-shield-check"></i> Admin Dashboard</h1>
    <span class="badge bg-primary fs-6">Chào mừng, ${sessionScope.account.fullName}!</span>
</div>

<!-- Stats Cards -->
<div class="row mb-4">
    <div class="col-md-3 mb-3">
        <div class="card stats-card bg-primary text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>1,234</h4>
                        <p class="mb-0">Tổng Video</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-collection-play fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3 mb-3">
        <div class="card stats-card bg-success text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>56</h4>
                        <p class="mb-0">Danh mục</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-folder2-open fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3 mb-3">
        <div class="card stats-card bg-warning text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>789</h4>
                        <p class="mb-0">Người dùng</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-people fs-1"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3 mb-3">
        <div class="card stats-card bg-info text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4>12.5K</h4>
                        <p class="mb-0">Lượt xem</p>
                    </div>
                    <div class="align-self-center">
                        <i class="bi bi-eye fs-1"></i>
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
                    <a href="/admin/categories" class="btn btn-outline-primary">
                        <i class="bi bi-folder-plus"></i> Quản lý danh mục
                    </a>
                    <a href="/admin/videos" class="btn btn-outline-success">
                        <i class="bi bi-collection-play"></i> Quản lý video
                    </a>
                    <a href="/admin/users" class="btn btn-outline-warning">
                        <i class="bi bi-people"></i> Quản lý người dùng
                    </a>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-header">
                <h5><i class="bi bi-graph-up"></i> Thống kê gần đây</h5>
            </div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Video mới hôm nay</span>
                        <span class="badge bg-primary">23</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Người dùng mới</span>
                        <span class="badge bg-success">5</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Lượt xem hôm nay</span>
                        <span class="badge bg-info">1,234</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>