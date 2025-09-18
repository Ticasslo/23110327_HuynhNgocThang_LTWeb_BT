<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="card">
                <div class="card-body">
                    <h4 class="mb-3"><i class="bi bi-folder2-open"></i> <c:choose><c:when test="${empty category.id}">Thêm danh mục</c:when><c:otherwise>Chỉnh sửa danh mục</c:otherwise></c:choose></h4>
                    <form method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label class="form-label">Tên danh mục</label>
                            <input type="text" class="form-control" name="categoryName" value="${category.categoryName}" required />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Hình ảnh danh mục</label>
                            <input type="file" class="form-control" name="file" accept="image/*" />
                            <c:if test="${not empty category.images}">
                                <div class="mt-2">
                                    <img src="/image?fname=${category.images}" alt="preview" style="height:60px; border-radius:6px;"/>
                                </div>
                            </c:if>
                        </div>
                        <div class="d-flex justify-content-between">
                            <a href="/admin/categories" class="btn btn-outline-secondary"><i class="bi bi-arrow-left"></i> Quay lại</a>
                            <button class="btn btn-primary" type="submit"><i class="bi bi-save"></i> Lưu</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


