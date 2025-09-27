<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<div class="list-group list-group-flush">
  <a href="${pageContext.request.contextPath}/admin/books" class="list-group-item list-group-item-action${fn:endsWith(pageContext.request.requestURI, '/admin/books') ? ' active' : ''}">📚 Quản lý sách</a>
  <a href="${pageContext.request.contextPath}/admin/authors" class="list-group-item list-group-item-action${fn:endsWith(pageContext.request.requestURI, '/admin/authors') ? ' active' : ''}">✍️ Quản lý tác giả</a>
</div>