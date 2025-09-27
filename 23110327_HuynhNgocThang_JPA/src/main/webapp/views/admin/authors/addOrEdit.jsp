<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="container py-3">
  <h4 class="mb-3"><i class="bi bi-person-badge"></i> ${author.authorId==0? 'Thêm' : 'Cập nhật'} tác giả</h4>
  <c:if test="${not empty alert}">
    <div class="alert alert-danger">${alert}</div>
  </c:if>
  <form action="${pageContext.request.contextPath}/admin/authors/saveOrUpdate" method="post">
    <input type="hidden" name="id" value="${author.authorId}" />
    <div class="row g-3">
      <div class="col-md-6">
        <label class="form-label">Tên tác giả</label>
        <input type="text" class="form-control" name="authorName" value="${author.authorName}" required />
      </div>
      <div class="col-md-6">
        <label class="form-label">Ngày sinh</label>
        <input type="date" class="form-control" name="dateOfBirth" value="${author.dateOfBirth}" />
      </div>
    </div>
    <div class="mt-3">
      <a href="${pageContext.request.contextPath}/admin/authors" class="btn btn-secondary">Quay lại</a>
      <button class="btn btn-primary">Lưu</button>
    </div>
  </form>
</div>
