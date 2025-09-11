<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quên mật khẩu</title>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

<style>
.password-requirements {
	font-size: 0.875rem;
	color: #6c757d;
}
.password-requirements .requirement {
	margin: 2px 0;
}
.password-requirements .requirement.valid {
	color: #28a745;
}
.password-requirements .requirement.invalid {
	color: #dc3545;
}
</style>

</head>
<body>

	<!-- Include topbar -->
	<jsp:include page="topbar.jsp" />

	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<div class="card-body">
						<h3 class="card-title text-center mb-4">Quên mật khẩu</h3>

						<c:if test="${alert != null}">
							<div class="alert alert-success">${alert}</div>
						</c:if>

						<c:if test="${error != null}">
							<div class="alert alert-danger">${error}</div>
						</c:if>

						<!-- Bước 1: Nhập username -->
						<c:if test="${step == null || step == 1}">
							<form action="forgot-password" method="post">
								<input type="hidden" name="step" value="1">
								<div class="mb-3">
									<label class="form-label">Tên tài khoản</label>
									<div class="input-group">
										<span class="input-group-text"> <i class="fa fa-user"></i>
										</span> <input type="text" placeholder="Nhập tên tài khoản"
											name="identifier" class="form-control" required
											value="${param.identifier}">
									</div>
									<div class="form-text">Nhập tên tài khoản hoặc email để tiếp tục</div>
								</div>

								<button type="submit" class="btn btn-primary w-100 mb-3">
									<i class="fa fa-arrow-right"></i> Tiếp tục
								</button>
							</form>
						</c:if>

						<!-- Bước 2: Tạo mật khẩu mới -->
						<c:if test="${step == 2}">
							<div class="alert alert-success">
								<i class="fa fa-check-circle"></i> Xác thực thành công! Vui lòng
								tạo mật khẩu mới cho tài khoản <strong>${username}</strong>
							</div>

							<form action="forgot-password" method="post" id="resetPasswordForm">
								<input type="hidden" name="step" value="2"> 
								<input type="hidden" name="username" value="${username}">

								<div class="mb-3">
									<label class="form-label">Mật khẩu mới</label>
									<div class="input-group">
										<span class="input-group-text"> <i class="fa fa-lock"></i>
										</span> <input type="password" placeholder="Nhập mật khẩu mới"
											name="newPassword" class="form-control" required
											id="newPassword">
										<button type="button" class="btn btn-outline-secondary"
											onclick="togglePassword('newPassword', this)">
											<i class="fa fa-eye"></i>
										</button>
									</div>
								</div>

								<div class="mb-3">
									<label class="form-label">Xác nhận mật khẩu mới</label>
									<div class="input-group">
										<span class="input-group-text"> < i class="fa fa-lock"></i>
										</span> <input type="password" placeholder="Nhập lại mật khẩu mới"
											name="confirmPassword" class="form-control" required
											id="confirmPassword">
										<button type="button" class="btn btn-outline-secondary"
											onclick="togglePassword('confirmPassword', this)">
											<i class="fa fa-eye"></i>
										</button>
									</div>
								</div>

								<button type="submit" class="btn btn-success w-100 mb-3">
									<i class="fa fa-key"></i> Đặt lại mật khẩu
								</button>

								<a href="forgot-password" class="btn btn-secondary w-100"> 
									<i class="fa fa-arrow-left"></i> Quay lại
								</a>
							</form>
						</c:if>

						<div class="text-center mt-3">
							<a href="${pageContext.request.contextPath}/login"
								class="btn btn-link"> <i class="fa fa-arrow-left"></i> Quay
								lại đăng nhập
							</a>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		// Toggle hiển thị mật khẩu
		function togglePassword(inputId, button) {
			const input = document.getElementById(inputId);
			const icon = button.querySelector('i');
			
			if (input.type === 'password') {
				input.type = 'text';
				icon.className = 'fa fa-eye-slash';
			} else {
				input.type = 'password';
				icon.className = 'fa fa-eye';
			}
		}
	</script>

</body>
</html>
