<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<div class="list-group list-group-flush">
  <a href="${pageContext.request.contextPath}/admin/category/list" class="list-group-item list-group-item-action${fn:endsWith(pageContext.request.requestURI, '/admin/category/list') ? ' active' : ''}">📋 Danh sách danh mục</a>
  <a href="${pageContext.request.contextPath}/admin/category/add" class="list-group-item list-group-item-action${fn:endsWith(pageContext.request.requestURI, '/admin/category/add') ? ' active' : ''}">➕ Thêm danh mục</a>
  <a href="${pageContext.request.contextPath}/admin/profile" class="list-group-item list-group-item-action${fn:endsWith(pageContext.request.requestURI, '/admin/profile') ? ' active' : ''}">👤 Thông tin cá nhân</a>
</div>