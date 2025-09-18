<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<sitemesh:write property="head">
    <style>
        .profile-container {
            max-width: 800px;
            margin: 30px auto;
            background: linear-gradient(180deg,#f8f9fa 0%,#e9ecef 100%);
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0,0,0,.1);
            border: 1px solid #dee2e6;
            backdrop-filter: blur(6px);
        }
        
        .profile-header {
            text-align: center;
            margin-bottom: 30px;
            color: #495057;
        }
        
        .profile-avatar {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #6c757d;
            margin: 0 auto 20px;
            display: block;
        }
        
        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 8px;
        }
        
        .form-control {
            border: 2px solid #e5e7eb;
            border-radius: 8px;
            padding: 12px 16px;
            font-size: 16px;
            transition: all 0.3s ease;
        }
        
        .form-control:focus {
            border-color: #6c757d;
            box-shadow: 0 0 0 3px rgba(108, 117, 125, 0.1);
        }
        
        .btn-update {
            background: linear-gradient(135deg, #6c757d 0%, #495057 100%);
            border: none;
            color: white;
            padding: 12px 30px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        
        .btn-update:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(108, 117, 125, 0.3);
        }
        
        .alert {
            border-radius: 8px;
            border: none;
            padding: 12px 16px;
            margin-bottom: 20px;
        }
        
        .alert-success {
            background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
            color: #065f46;
        }
        
        .alert-danger {
            background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
            color: #991b1b;
        }
        
        .role-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        .role-admin {
            background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
            color: white;
        }
        
        .role-manager {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            color: white;
        }
        
        .role-user {
            background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
            color: white;
        }
    </style>
</sitemesh:write>

<div class="profile-container">
    <div class="profile-header">
        <h1><i class="bi bi-person-circle"></i> Thông tin cá nhân</h1>
    </div>
    
    <!-- Hiển thị thông báo -->
    <c:if test="${not empty success}">
        <div class="alert alert-success">
            <i class="bi bi-check-circle"></i> ${success}
        </div>
    </c:if>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            <i class="bi bi-exclamation-triangle"></i> ${error}
        </div>
    </c:if>
    
    <c:choose>
        <c:when test="${user.roleid == 1}">
            <c:set var="profileAction" value="${pageContext.request.contextPath}/admin/profile" />
        </c:when>
        <c:when test="${user.roleid == 2}">
            <c:set var="profileAction" value="${pageContext.request.contextPath}/manager/profile" />
        </c:when>
        <c:otherwise>
            <c:set var="profileAction" value="${pageContext.request.contextPath}/user/profile" />
        </c:otherwise>
    </c:choose>
    
    <form action="${profileAction}" method="post" enctype="multipart/form-data">
        <div class="row">
            <!-- Avatar -->
            <div class="col-12 text-center mb-4">
                <c:choose>
                    <c:when test="${not empty user.avatar}">
                        <c:url value="/image?fname=${user.avatar}" var="imgUrl"></c:url>
                        <img src="${imgUrl}" alt="Avatar" class="profile-avatar" id="avatarPreview">
                    </c:when>
                    <c:otherwise>
                        <img src="https://via.placeholder.com/150/6c757d/ffffff?text=${user.userName}" alt="Avatar" class="profile-avatar" id="avatarPreview">
                    </c:otherwise>
                </c:choose>
            </div>
            
            <!-- Thông tin cơ bản -->
            <div class="col-md-6 mb-3">
                <label for="userName" class="form-label">Tên đăng nhập</label>
                <input type="text" class="form-control" id="userName" value="${user.userName}" readonly>
            </div>
            
            <div class="col-md-6 mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" value="${user.email}" readonly>
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
                <label for="role" class="form-label">Vai trò</label>
                <div class="form-control" style="background-color: #f8f9fa; border: 1px solid #dee2e6;">
                    <c:choose>
                        <c:when test="${user.roleid == 1}">
                            <span class="role-badge role-admin">Quản trị viên</span>
                        </c:when>
                        <c:when test="${user.roleid == 2}">
                            <span class="role-badge role-manager">Quản lý</span>
                        </c:when>
                        <c:otherwise>
                            <span class="role-badge role-user">Người dùng</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            <div class="col-md-6 mb-3">
                <label for="createdDate" class="form-label">Ngày tạo</label>
                <input type="text" class="form-control" id="createdDate" value="${user.createdDate}" readonly>
            </div>
            
            <!-- Upload avatar -->
            <div class="col-12 mb-4">
                <label for="avatar" class="form-label">Ảnh đại diện</label>
                <input type="file" class="form-control" id="avatar" name="avatar" accept="image/*" onchange="previewImage(this)">
                <div class="form-text">Chọn ảnh mới để cập nhật avatar (JPG, PNG, GIF - tối đa 5MB)</div>
            </div>
            
            <!-- Nút cập nhật -->
            <div class="col-12 text-center">
                <button type="submit" class="btn btn-update">
                    <i class="bi bi-save"></i> Cập nhật thông tin
                </button>
            </div>
        </div>
    </form>
</div>

<sitemesh:write property="page.scripts">
    <script>
        function previewImage(input) {
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('avatarPreview').src = e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
        }
        
        // Validate phone number
        document.getElementById('phone').addEventListener('input', function(e) {
            const phone = e.target.value;
            const phoneRegex = /^[0-9+\-\s()]*$/;
            if (phone && !phoneRegex.test(phone)) {
                e.target.setCustomValidity('Số điện thoại chỉ được chứa số, dấu +, -, khoảng trắng và dấu ngoặc');
            } else {
                e.target.setCustomValidity('');
            }
        });
    </script>
</sitemesh:write>
