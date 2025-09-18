<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="list-group list-group-flush">
    <a href="/" class="list-group-item list-group-item-action">
        <i class="bi bi-house"></i> Trang chủ
    </a>
    <a href="/search" class="list-group-item list-group-item-action">
        <i class="bi bi-search"></i> Tìm kiếm
    </a>
    <c:if test="${not empty sessionScope.account}">
        <a href="/profile" class="list-group-item list-group-item-action">
            <i class="bi bi-person"></i> Thông tin cá nhân
        </a>
    </c:if>
</div>