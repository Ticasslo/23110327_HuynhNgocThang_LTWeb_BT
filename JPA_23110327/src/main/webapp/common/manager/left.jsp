<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<div class="list-group list-group-flush">
  <a href="${pageContext.request.contextPath}/manager/category/list" class="list-group-item list-group-item-action${fn:endsWith(pageContext.request.requestURI, '/manager/category/list') ? ' active' : ''}">📋 Danh sách danh mục</a>
  <a href="${pageContext.request.contextPath}/manager/category/add" class="list-group-item list-group-item-action${fn:endsWith(pageContext.request.requestURI, '/manager/category/add') ? ' active' : ''}">➕ Thêm danh mục</a>
</div>
