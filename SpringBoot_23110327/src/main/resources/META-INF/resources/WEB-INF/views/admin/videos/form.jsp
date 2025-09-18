<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="m-0">
            <i class="bi bi-collection-play"></i> 
            <c:choose>
                <c:when test="${empty video.video_id}">
                    Thêm video mới
                </c:when>
                <c:otherwise>
                    Chỉnh sửa video: ${video.title}
                </c:otherwise>
            </c:choose>
        </h3>
        <a href="/admin/videos" class="btn btn-secondary"><i class="bi bi-arrow-left"></i> Quay lại</a>
    </div>

    <div class="card">
        <div class="card-body">
            <form action="/admin/videos/${empty video.video_id ? 'add' : 'edit/' concat video.video_id}" method="post" enctype="multipart/form-data">
                <div class="row">
                    <div class="col-md-8 mb-3">
                        <label for="title" class="form-label">Tiêu đề <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="title" name="title" value="${video.title}" required>
                    </div>
                    
                    <div class="col-md-4 mb-3">
                        <label for="category_id" class="form-label">Danh mục <span class="text-danger">*</span></label>
                        <select class="form-select" id="category_id" name="category_id" required>
                            <option value="">-- Chọn danh mục --</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.id}" ${video.category != null && video.category.id == cat.id ? 'selected' : ''}>
                                    ${cat.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="col-12 mb-3">
                        <label for="description" class="form-label">Mô tả</label>
                        <textarea class="form-control" id="description" name="description" rows="4">${video.description}</textarea>
                    </div>
                    
                    <div class="col-md-6 mb-3">
                        <label for="posterFile" class="form-label">Poster (Ảnh đại diện)</label>
                        <input type="file" class="form-control" id="posterFile" name="posterFile" accept="image/*">
                        <c:if test="${not empty video.poster}">
                            <div class="mt-2">
                                <p class="mb-1">Poster hiện tại:</p>
                                <img src="/image?fname=${video.poster}" alt="Current Poster" style="max-width: 200px; border-radius: 8px;">
                                <input type="hidden" name="poster" value="${video.poster}">
                            </div>
                        </c:if>
                    </div>
                    
                    <c:if test="${not empty video.video_id}">
                        <div class="col-md-6 mb-3">
                            <label for="views" class="form-label">Lượt xem</label>
                            <input type="number" class="form-control" id="views" name="views" value="${video.views}" min="0">
                        </div>
                    </c:if>
                </div>
                
                <div class="text-end">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Lưu
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
