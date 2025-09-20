<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="m-0"><i class="bi bi-folder2-open"></i> Danh mục</h3>
        <a href="/admin/categories/add" class="btn btn-primary"><i class="bi bi-plus"></i> Thêm danh mục</a>
    </div>

    <c:if test="${not empty alert}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle"></i> ${alert}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <c:if test="${param.success == '1'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle"></i> Xóa danh mục thành công!
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <form class="row g-2 mb-3" method="get">
        <div class="col-sm-4 col-md-3">
            <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tìm theo tên..." />
        </div>
        <div class="col-auto">
            <button class="btn btn-outline-primary" type="submit"><i class="bi bi-search"></i> Tìm kiếm</button>
        </div>
    </form>

    <div class="card">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th style="width: 80px;">ID</th>
                        <th>Tên danh mục</th>
                        <th style="width: 140px;">Biểu tượng</th>
                        <th style="width: 160px;" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${items}">
                        <tr>
                            <td>${c.id}</td>
                            <td>${c.categoryName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty c.images}">
                                        <img src="/image?fname=${c.images}" alt="icon" style="height:32px; border-radius:6px;"/>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">(chưa có)</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-secondary" href="/admin/categories/edit/${c.id}"><i class="bi bi-pencil"></i></a>
                                <form action="/admin/categories/delete/${c.id}" method="post" class="d-inline" onsubmit="return confirm('Xóa danh mục này?')">
                                    <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty items}">
                        <tr>
                            <td colspan="4" class="text-center text-muted py-4">Không có dữ liệu</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>


