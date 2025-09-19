<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="m-0">
            <i class="bi bi-people"></i> 
            <c:choose>
                <c:when test="${user.id == 0}">
                    Thêm người dùng mới
                </c:when>
                <c:otherwise>
                    Chỉnh sửa người dùng: ${user.fullName}
                </c:otherwise>
            </c:choose>
        </h3>
        <a href="/admin/users" class="btn btn-secondary"><i class="bi bi-arrow-left"></i> Quay lại</a>
    </div>

    <div class="card">
        <div class="card-body">
            <c:if test="${not empty alert}">
                <div class="alert alert-warning mb-3">${alert}</div>
            </c:if>
            <c:choose>
                <c:when test="${user.id == 0}">
                    <c:set var="formAction" value="/admin/users/add"/>
                </c:when>
                <c:otherwise>
                    <c:set var="formAction" value="/admin/users/edit/${user.id}"/>
                </c:otherwise>
            </c:choose>
            <form action="${formAction}" method="post" accept-charset="UTF-8">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="userName" class="form-label">Tên đăng nhập <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="userName" name="userName" value="${user.userName}" required>
                    </div>
                    
                    <div class="col-md-6 mb-3">
                        <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                    </div>
                    
                    <div class="col-md-6 mb-3">
                        <label for="fullName" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullName}" required>
                    </div>
                    
                    <div class="col-md-6 mb-3">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input type="tel" class="form-control" id="phone" name="phone" value="${user.phone}">
                    </div>
                    
                    <div class="col-md-6 mb-3">
                        <label for="roleid" class="form-label">Vai trò <span class="text-danger">*</span></label>
                        <select class="form-select" id="roleid" name="roleid" required>
                            <option value="">-- Chọn vai trò --</option>
                            <option value="1" ${user.roleid == 1 ? 'selected' : ''}>Admin</option>
                            <option value="2" ${user.roleid == 2 ? 'selected' : ''}>User</option>
                        </select>
                    </div>
                    
                    <c:if test="${user.id == 0}">
                        <div class="col-md-6 mb-3">
                            <label for="password" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" id="password" name="password" required>
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
