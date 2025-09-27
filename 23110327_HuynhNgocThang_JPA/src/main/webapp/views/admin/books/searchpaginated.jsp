<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="m-0"><i class="bi bi-book"></i> Danh sách sách</h3>
    <a href="${pageContext.request.contextPath}/admin/books/add" class="btn btn-primary"><i class="bi bi-plus"></i> Thêm sách</a>
  </div>

  <form action="${pageContext.request.contextPath}/admin/books" method="get" class="mb-3">
    <div class="input-group">
      <input type="text" class="form-control" name="keyword" id="keyword" value="${keyword}" placeholder="Nhập từ khóa để tìm" />
      <button class="btn btn-outline-primary" type="submit"><i class="bi bi-search"></i> Tìm kiếm</button>
    </div>
  </form>

  <div class="card">
    <div class="card-header">Danh sách sách</div>
    <div class="card-body">
      <c:if test="${empty books}">
        <div class="alert alert-danger" role="alert">
          <i class="bi bi-exclamation-triangle"></i> Không tìm thấy sách nào
        </div>
      </c:if>

      <c:if test="${not empty books}">
      <table class="table table-striped table-responsive">
        <thead>
          <tr>
            <th style="width:80px;">ID</th>
            <th style="width:100px;">Ảnh bìa</th>
            <th>Tiêu đề</th>
            <th>ISBN</th>
            <th>Nhà xuất bản</th>
            <th>Giá</th>
            <th style="width:160px;" class="text-end">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="b" items="${books}">
            <tr>
              <td>${b.bookId}</td>
              <td>
                <c:choose>
                  <c:when test="${b.coverImage != null}">
                    <img src="${pageContext.request.contextPath}/uploads_23110327/${b.coverImage}" 
                         alt="Ảnh bìa" class="img-thumbnail" style="max-width: 60px; max-height: 60px;">
                  </c:when>
                  <c:otherwise>
                    <div class="bg-light text-center text-muted" style="width: 60px; height: 60px; line-height: 60px; font-size: 12px;">
                      No Image
                    </div>
                  </c:otherwise>
                </c:choose>
              </td>
              <td>${b.title}</td>
              <td>${b.isbn}</td>
              <td>${b.publisher}</td>
              <td>${b.price}</td>
              <td class="text-end">
                <a href="${pageContext.request.contextPath}/admin/books/edit?id=${b.bookId}" class="btn btn-sm btn-outline-secondary"><i class="bi bi-pencil"></i></a>
                <form action="${pageContext.request.contextPath}/admin/books/delete" method="post" class="d-inline" onsubmit="return confirm('Xóa sách này?')">
                  <input type="hidden" name="id" value="${b.bookId}" />
                  <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      </c:if>
    </div>

    <div class="card-footer text-muted">
      <div class="row align-items-center">
        <div class="col-3">
          <form method="get" action="${pageContext.request.contextPath}/admin/books">
            <c:if test="${not empty keyword}">
              <input type="hidden" name="keyword" value="${keyword}" />
            </c:if>
            <div class="input-group">
              <label for="size" class="input-group-text">Kích thước:</label>
              <select class="form-select" name="size" id="size" onchange="this.form.submit()">
                <option value="3" ${size==3? 'selected' : ''}>3</option>
                <option value="5" ${size==5? 'selected' : ''}>5</option>
                <option value="10" ${size==10? 'selected' : ''}>10</option>
                <option value="15" ${size==15? 'selected' : ''}>15</option>
                <option value="20" ${size==20? 'selected' : ''}>20</option>
              </select>
            </div>
          </form>
        </div>
        <div class="col-9">
          <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center mb-0">
              <li class="page-item ${page==1? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/books?keyword=${keyword}&size=${size}&page=1">Đầu</a>
              </li>
              <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i==page? 'active' : ''}">
                  <a class="page-link" href="${pageContext.request.contextPath}/admin/books?keyword=${keyword}&size=${size}&page=${i}">${i}</a>
                </li>
              </c:forEach>
              <li class="page-item ${page==totalPages? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/books?keyword=${keyword}&size=${size}&page=${totalPages}">Cuối</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</div>
