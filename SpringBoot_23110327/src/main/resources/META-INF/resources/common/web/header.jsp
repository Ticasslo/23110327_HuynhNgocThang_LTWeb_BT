<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="/">
            <i class="bi bi-play-circle"></i> Video Manager
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/search">Tìm kiếm</a>
                </li>
            </ul>
            
            <!-- Thông tin tác giả -->
            <div class="navbar-nav me-3">
                <div class="nav-item d-flex align-items-center">
                    <c:if test="${not empty authorAvatar}">
                        <img src="/image?fname=${authorAvatar}" alt="Author Avatar" 
                             style="width: 32px; height: 32px; border-radius: 50%; object-fit: cover; margin-right: 8px;"
                             onerror="this.style.display='none'; this.nextElementSibling.style.display='inline-block';">
                        <div style="width: 32px; height: 32px; border-radius: 50%; background-color: #ffffff20; display: none; align-items: center; justify-content: center; margin-right: 8px; color: white; font-weight: bold;">
                            ${authorName.substring(0,1)}
                        </div>
                    </c:if>
                    <small class="text-light">
                        <strong>Tên: ${authorName}</strong>
                        <br>
                        <span style="font-size: 0.75em;">MSSV: ${authorStudentId}</span>
                    </small>
                </div>
            </div>
            
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                <i class="bi bi-person-circle"></i> ${sessionScope.account.fullName}
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="/logout">Đăng xuất</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="btn btn-outline-light me-2" href="/login">Đăng nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-light" href="/register">Đăng ký</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>