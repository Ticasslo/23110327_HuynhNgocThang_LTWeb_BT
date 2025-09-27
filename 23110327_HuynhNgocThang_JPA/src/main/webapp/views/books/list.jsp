<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="m-0"><i class="bi bi-book"></i> Tất cả sách</h3>
  </div>

  <div class="row g-3">
    <c:forEach var="item" items="${bookData}">
      <c:set var="bookId" value="${item[0]}" />
      <c:set var="isbn" value="${item[1]}" />
      <c:set var="title" value="${item[2]}" />
      <c:set var="publisher" value="${item[3]}" />
      <c:set var="price" value="${item[4]}" />
      <c:set var="publishDate" value="${item[5]}" />
      <c:set var="coverImage" value="${item[6]}" />
      <c:set var="quantity" value="${item[7]}" />
      <c:set var="authors" value="${item[8]}" />
      <c:set var="reviewCount" value="${item[9]}" />
      <div class="col-md-6">
        <div class="border rounded p-3 h-100">
          <div class="row g-3">
      <div class="col-4">
        <c:choose>
          <c:when test="${coverImage != null}">
            <img src="${pageContext.request.contextPath}/uploads_23110327/${coverImage}" 
                 alt="Ảnh bìa" class="img-fluid rounded" style="max-height: 200px; object-fit: cover;">
          </c:when>
          <c:otherwise>
            <div class="ratio ratio-3x4 border bg-light d-flex align-items-center justify-content-center text-muted">
              <small>[cover_image]</small>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
            <div class="col-8">
              <div><strong>Tiêu đề:</strong> <a href="${pageContext.request.contextPath}/bookdetail?id=${bookId}" class="text-decoration-none">${title}</a></div>
              <div><strong>Mã isbn:</strong> ${isbn}</div>
              <div><strong>Tác giả:</strong> 
                <em>${authors != null ? authors : 'Chưa có'}</em>
              </div>
              <div><strong>Publisher:</strong> ${publisher}</div>
              <div><strong>Publisher_date:</strong> ${publishDate}</div>
              <div><strong>Quantity:</strong> ${quantity}</div>
              <div><strong>Review (</strong>${reviewCount}<strong>)</strong></div>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <div class="mt-3">
    <nav aria-label="Page navigation">
      <ul class="pagination justify-content-center">
        <li class="page-item ${page==1? 'active' : ''}">
          <a class="page-link" href="${pageContext.request.contextPath}/booklist?size=${size}&page=1">Đầu</a>
        </li>
        <c:forEach var="i" begin="1" end="${totalPages}">
          <li class="page-item ${i==page? 'active' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/booklist?size=${size}&page=${i}">${i}</a>
          </li>
        </c:forEach>
        <li class="page-item ${page==totalPages? 'active' : ''}">
          <a class="page-link" href="${pageContext.request.contextPath}/booklist?size=${size}&page=${totalPages}">Cuối</a>
        </li>
      </ul>
    </nav>
  </div>
</div>
