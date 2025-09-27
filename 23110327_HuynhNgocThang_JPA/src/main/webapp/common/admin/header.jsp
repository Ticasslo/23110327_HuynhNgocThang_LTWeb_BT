<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/home">
      <i class="bi bi-shield-lock"></i> Admin
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarAdmin">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarAdmin">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/"><i class="bi bi-house"></i> Trang chủ</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/booklist"><i class="bi bi-book"></i> Sản phẩm</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/home"><i class="bi bi-shield-lock"></i> Trang quản trị</a></li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item d-flex align-items-center me-3">
          <i class="bi bi-person-circle me-2" style="font-size: 20px;"></i>
          <span class="navbar-text">
            <c:out value="${sessionScope.account.fullname}"/>
            <c:if test="${empty sessionScope.account.fullname}">(<c:out value="${sessionScope.account.email}"/>)</c:if>
          </span>
        </li>
        <li class="nav-item"><a class="btn btn-outline-light" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
      </ul>
    </div>
  </div>
</nav>