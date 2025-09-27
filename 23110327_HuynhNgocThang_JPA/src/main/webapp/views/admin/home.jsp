<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<main class="container py-5">
  <div class="p-4 bg-light border rounded-3 text-center">
    <h3 class="mb-2">Xin chào, Quản trị viên</h3>
    <p class="text-muted mb-0">
      <c:choose>
        <c:when test="${not empty sessionScope.account.fullname}">${sessionScope.account.fullname}</c:when>
        <c:otherwise>${sessionScope.account.email}</c:otherwise>
      </c:choose>
    </p>
  </div>
</main>

