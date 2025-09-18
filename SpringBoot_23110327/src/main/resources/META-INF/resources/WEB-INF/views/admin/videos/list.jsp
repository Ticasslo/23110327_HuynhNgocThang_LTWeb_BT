<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="m-0"><i class="bi bi-collection-play"></i> Video</h3>
        <a href="/admin/videos/add" class="btn btn-primary"><i class="bi bi-plus"></i> Thêm video</a>
    </div>

    <form class="row g-2 mb-3" method="get">
        <div class="col-sm-4 col-md-3">
            <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tìm theo tiêu đề..." />
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
                        <th style="width: 120px;">Poster</th>
                        <th>Tiêu đề</th>
                        <th>Mô tả</th>
                        <th>Danh mục</th>
                        <th style="width: 100px;">Lượt xem</th>
                        <th style="width: 160px;" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="v" items="${items}">
                        <tr>
                            <td>${v.video_id}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty v.poster}">
                                        <img src="/image?fname=${v.poster}" alt="poster" style="width:80px; height:60px; border-radius:6px; object-fit:cover;"/>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="width:80px; height:60px; border-radius:6px; background-color:#6c757d; display:flex; align-items:center; justify-content:center; color:white;">
                                            <i class="bi bi-play-circle"></i>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div style="max-width: 200px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                                    ${v.title}
                                </div>
                            </td>
                            <td>
                                <div style="max-width: 300px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                                    ${v.description}
                                </div>
                            </td>
                            <td>
                                <c:if test="${not empty v.category}">
                                    <span class="badge bg-info">${v.category.categoryName}</span>
                                </c:if>
                            </td>
                            <td>
                                <span class="badge bg-secondary">${v.views}</span>
                            </td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-secondary" href="/admin/videos/edit/${v.video_id}"><i class="bi bi-pencil"></i></a>
                                <form action="/admin/videos/delete/${v.video_id}" method="post" class="d-inline" onsubmit="return confirm('Xóa video này?')">
                                    <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty items}">
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">Không có dữ liệu</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
