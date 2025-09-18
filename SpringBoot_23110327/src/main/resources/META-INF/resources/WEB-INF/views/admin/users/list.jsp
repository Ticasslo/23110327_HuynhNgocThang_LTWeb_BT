<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="m-0"><i class="bi bi-people"></i> Người dùng</h3>
        <a href="/admin/users/add" class="btn btn-primary"><i class="bi bi-plus"></i> Thêm người dùng</a>
    </div>

    <form class="row g-2 mb-3" method="get">
        <div class="col-sm-4 col-md-3">
            <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="Tìm theo tên hoặc username..." />
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
                        <th>Avatar</th>
                        <th>Tên đăng nhập</th>
                        <th>Họ và tên</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Vai trò</th>
                        <th>Ngày tạo</th>
                        <th style="width: 160px;" class="text-end">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${items}">
                        <tr>
                            <td>${u.id}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty u.avatar}">
                                        <img src="/image?fname=${u.avatar}" alt="avatar" style="width:40px; height:40px; border-radius:50%; object-fit:cover;"/>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="width:40px; height:40px; border-radius:50%; background-color:#6c757d; display:flex; align-items:center; justify-content:center; color:white; font-weight:bold;">
                                            ${fn:substring(u.fullName, 0, 1)}
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${u.userName}</td>
                            <td>${u.fullName}</td>
                            <td>${u.email}</td>
                            <td>${u.phone}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.roleid == 1}">
                                        <span class="badge bg-danger">Admin</span>
                                    </c:when>
                                    <c:when test="${u.roleid == 2}">
                                        <span class="badge bg-success">User</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">Unknown</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${u.createdDate}</td>
                            <td class="text-end">
                                <a class="btn btn-sm btn-outline-secondary" href="/admin/users/edit/${u.id}"><i class="bi bi-pencil"></i></a>
                                <form action="/admin/users/delete/${u.id}" method="post" class="d-inline" onsubmit="return confirm('Xóa người dùng này?')">
                                    <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty items}">
                        <tr>
                            <td colspan="9" class="text-center text-muted py-4">Không có dữ liệu</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
