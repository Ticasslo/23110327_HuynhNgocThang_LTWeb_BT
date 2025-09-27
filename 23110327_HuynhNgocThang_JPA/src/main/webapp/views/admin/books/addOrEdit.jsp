<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="container py-3">
  <h4 class="mb-3"><i class="bi bi-book"></i> ${book.bookId==0? 'Thêm' : 'Cập nhật'} sách</h4>
  <c:if test="${not empty alert}">
    <div class="alert alert-danger">${alert}</div>
  </c:if>
  <form action="${pageContext.request.contextPath}/admin/books/saveOrUpdate" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${book.bookId}" />
    <div class="row g-3">
      <div class="col-md-6">
        <label class="form-label">Tiêu đề</label>
        <input type="text" class="form-control" name="title" value="${book.title}" required />
      </div>
      <div class="col-md-6">
        <label class="form-label">ISBN</label>
        <input type="number" class="form-control" name="isbn" value="${book.isbn}" />
      </div>
      <div class="col-md-6">
        <label class="form-label">Nhà xuất bản</label>
        <input type="text" class="form-control" name="publisher" value="${book.publisher}" />
      </div>
      <div class="col-md-6">
        <label class="form-label">Giá</label>
        <input type="number" step="0.01" class="form-control" name="price" value="${book.price}" />
      </div>
      <div class="col-12">
        <label class="form-label">Mô tả</label>
        <textarea class="form-control" name="description" rows="3">${book.description}</textarea>
      </div>
      <div class="col-md-6">
        <label class="form-label">Ngày xuất bản</label>
        <input type="date" class="form-control" name="publishDate" value="${book.publishDate}" />
      </div>
      <div class="col-md-6">
        <label class="form-label">Hình ảnh bìa</label>
        <c:if test="${book.bookId != 0 && book.coverImage != null}">
          <div class="mb-2">
            <img src="${pageContext.request.contextPath}/uploads_23110327/${book.coverImage}" 
                 alt="Ảnh hiện tại" class="img-thumbnail" style="max-width: 100px; max-height: 100px;">
            <input type="hidden" name="existingCoverImage" value="${book.coverImage}" />
          </div>
        </c:if>
        <input type="file" class="form-control" name="coverImageFile" accept="image/*" />
        <small class="text-muted">Chọn ảnh mới để thay thế (để trống giữ nguyên ảnh cũ)</small>
      </div>
      <div class="col-md-6">
        <label class="form-label">Số lượng</label>
        <input type="number" class="form-control" name="quantity" value="${book.quantity}" />
      </div>
    </div>
    <div class="mt-3">
      <a href="${pageContext.request.contextPath}/admin/books" class="btn btn-secondary">Quay lại</a>
      <button class="btn btn-primary">Lưu</button>
    </div>
  </form>
</div>
