<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-dark" style="background: linear-gradient(90deg,#1d4ed8 0%, #0ea5e9 100%);">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/user/home">
      <i class="bi bi-person-circle"></i> User
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarUser">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarUser">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Trang chủ</a></li>
      </ul>
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${not empty sessionScope.account}">
            <li class="nav-item d-flex align-items-center me-3">
              <c:choose>
                <c:when test="${not empty sessionScope.account.avatar}">
                  <c:url value="/image?fname=${sessionScope.account.avatar}" var="avatarUrl"></c:url>
                  <img src="${avatarUrl}" alt="Avatar" class="rounded-circle me-2" style="width: 32px; height: 32px; object-fit: cover;">
                </c:when>
                <c:otherwise>
                  <i class="bi bi-person-circle me-2" style="font-size: 24px;"></i>
                </c:otherwise>
              </c:choose>
              <span class="navbar-text">${sessionScope.account.fullName}</span>
            </li>
            <li class="nav-item"><a class="btn btn-outline-light" href="${pageContext.request.contextPath}/logout" onclick="return confirm('Đăng xuất?')">Đăng xuất</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item"><a class="btn btn-outline-light" href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>
