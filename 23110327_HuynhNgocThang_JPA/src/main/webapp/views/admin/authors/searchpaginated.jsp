<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="container-fluid">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="m-0"><i class="bi bi-person-badge"></i> Danh sách tác giả</h3>
    <a href="${pageContext.request.contextPath}/admin/authors/add" class="btn btn-primary"><i class="bi bi-plus"></i> Thêm tác giả</a>
  </div>

  <form action="${pageContext.request.contextPath}/admin/authors" method="get" class="mb-3">
    <div class="input-group">
      <input type="text" class="form-control" name="keyword" id="keyword" value="${keyword}" placeholder="Nhập từ khóa để tìm" />
      <button class="btn btn-outline-primary" type="submit"><i class="bi bi-search"></i> Tìm kiếm</button>
    </div>
  </form>

  <div class="card">
    <div class="card-header">Danh sách tác giả</div>
    <div class="card-body">
      <c:if test="${empty authors}">
        <div class="alert alert-danger" role="alert">
          <i class="bi bi-exclamation-triangle"></i> Không tìm thấy tác giả nào
        </div>
      </c:if>

      <c:if test="${not empty authors}">
      <table class="table table-striped table-responsive">
        <thead>
          <tr>
            <th style="width:80px;">ID</th>
            <th>Tên tác giả</th>
            <th>Ngày sinh</th>
            <th style="width:160px;" class="text-end">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="a" items="${authors}">
            <tr>
              <td>${a.authorId}</td>
              <td>${a.authorName}</td>
              <td>${a.dateOfBirth}</td>
              <td class="text-end">
                <a href="${pageContext.request.contextPath}/admin/authors/edit?id=${a.authorId}" class="btn btn-sm btn-outline-secondary"><i class="bi bi-pencil"></i></a>
                <form action="${pageContext.request.contextPath}/admin/authors/delete" method="post" class="d-inline" onsubmit="return confirm('Xóa tác giả này?')">
                  <input type="hidden" name="id" value="${a.authorId}" />
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
          <form method="get" action="${pageContext.request.contextPath}/admin/authors">
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
                <a class="page-link" href="${pageContext.request.contextPath}/admin/authors?keyword=${keyword}&size=${size}&page=1">Đầu</a>
              </li>
              <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i==page? 'active' : ''}">
                  <a class="page-link" href="${pageContext.request.contextPath}/admin/authors?keyword=${keyword}&size=${size}&page=${i}">${i}</a>
                </li>
              </c:forEach>
              <li class="page-item ${page==totalPages? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/admin/authors?keyword=${keyword}&size=${size}&page=${totalPages}">Cuối</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</div>
