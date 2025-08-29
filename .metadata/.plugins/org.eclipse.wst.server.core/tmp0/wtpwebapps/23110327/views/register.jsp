<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký</title>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

</head>
<body>

	<jsp:include page="topbar.jsp" />

	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<form action="register" method="post">
					<h2 class="text-center mb-4">Tạo tài khoản mới</h2>

					<c:if test="${alert != null}">
						<div class="alert alert-danger">${alert}</div>
					</c:if>

					<div class="row">
						<div class="col-md-6">
							<div class="mb-3">
								<label class="form-label">Tài khoản <span
									class="text-danger">*</span></label>
								<div class="input-group">
									<span class="input-group-text"> <i class="fa fa-user"></i>
									</span> <input type="text" placeholder="Nhập tài khoản"
										name="username" class="form-control" required
										value="${username}">
								</div>
							</div>
						</div>

						<div class="col-md-6">
							<div class="mb-3">
								<label class="form-label">Email <span
									class="text-danger">*</span></label>
								<div class="input-group">
									<span class="input-group-text"> <i
										class="fa fa-envelope"></i>
									</span> <input type="email" placeholder="Nhập email" name="email"
										class="form-control" required value="${email}">
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-6">
							<div class="mb-3">
								<label class="form-label">Họ và tên <span
									class="text-danger">*</span></label>
								<div class="input-group">
									<span class="input-group-text"> <i class="fa fa-id-card"></i>
									</span> <input type="text" placeholder="Nhập họ và tên"
										name="fullname" class="form-control" required
										value="${fullname}">
								</div>
							</div>
						</div>

						<div class="col-md-6">
							<div class="mb-3">
								<label class="form-label">Số điện thoại</label>
								<div class="input-group">
									<span class="input-group-text"> <i class="fa fa-phone"></i>
									</span> <input type="tel" placeholder="Nhập số điện thoại"
										name="phone" class="form-control" value="${phone}">
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-6">
							<div class="mb-3">
								<label class="form-label">Mật khẩu <span
									class="text-danger">*</span></label>
								<div class="input-group">
									<span class="input-group-text"> <i class="fa fa-lock"></i>
									</span> <input type="password" placeholder="Nhập mật khẩu"
										name="password" class="form-control" required>
								</div>
								<small class="form-text text-muted">Mật khẩu phải có ít
									nhất 6 ký tự</small>
							</div>
						</div>

						<div class="col-md-6">
							<div class="mb-3">
								<label class="form-label">Xác nhận mật khẩu <span
									class="text-danger">*</span></label>
								<div class="input-group">
									<span class="input-group-text"> <i class="fa fa-lock"></i>
									</span> <input type="password" placeholder="Nhập lại mật khẩu"
										name="confirmPassword" class="form-control" required>
								</div>
							</div>
						</div>
					</div>

					<button type="submit" class="btn btn-primary w-100 mb-3">Tạo
						tài khoản</button>

					<div class="text-center">
						<p>
							Đã có tài khoản? <a
								href="${pageContext.request.contextPath}/login">Đăng nhập
								ngay</a>
						</p>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

	<!-- Validation -->
	<script>
		document
				.querySelector('form')
				.addEventListener(
						'submit',
						function(e) {
							const password = document
									.querySelector('input[name="password"]').value;
							const confirmPassword = document
									.querySelector('input[name="confirmPassword"]').value;

							if (password !== confirmPassword) {
								e.preventDefault();
								alert('Mật khẩu xác nhận không khớp!');
								return false;
							}

							if (password.length < 6) {
								e.preventDefault();
								alert('Mật khẩu phải có ít nhất 6 ký tự!');
								return false;
							}
						});
	</script>

</body>
</html>