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
											name="username" class="form-control" required
											value="${param.username}">
									</div>
									<div class="form-text">Nhập tên tài khoản để tiếp tục</div>
								</div>

								<button type="submit" class="btn btn-primary w-100 mb-3">
									<i class="fa fa-arrow-right"></i> Tiếp tục
								</button>
							</form>
						</c:if>

						<!-- Bước 2: Xác thực email và phone -->
						<c:if test="${step == 2}">
							<div class="alert alert-info">
								<i class="fa fa-info-circle"></i> Để đảm bảo an toàn, vui lòng
								xác thực thông tin của bạn:
							</div>

							<form action="forgot-password" method="post">
								<input type="hidden" name="step" value="2"> 
								<input type="hidden" name="username" value="${username}">

								<div class="mb-3">
									<label class="form-label">Email xác thực</label>
									<div class="input-group">
										<span class="input-group-text"> <i
											class="fa fa-envelope"></i>
										</span> <input type="email" placeholder="Nhập email của bạn"
											name="email" class="form-control" required>
									</div>
									<div class="form-text">
										Email đã đăng ký với tài khoản <strong>${username}</strong>
									</div>
								</div>

								<div class="mb-3">
									<label class="form-label">Số điện thoại xác thực</label>
									<div class="input-group">
										<span class="input-group-text"> <i class="fa fa-phone"></i>
										</span> <input type="tel" placeholder="Nhập số điện thoại"
											name="phone" class="form-control" required>
									</div>
									<div class="form-text">
										Số điện thoại đã đăng ký với tài khoản <strong>${username}</strong>
									</div>
								</div>

								<button type="submit" class="btn btn-warning w-100 mb-3">
									<i class="fa fa-check"></i> Xác thực thông tin
								</button>

								<a href="forgot-password" class="btn btn-secondary w-100"> 
									<i class="fa fa-arrow-left"></i> Quay lại
								</a>
							</form>
						</c:if>

						<!-- Bước 3: Tạo mật khẩu mới -->
						<c:if test="${step == 3}">
							<div class="alert alert-success">
								<i class="fa fa-check-circle"></i> Xác thực thành công! Vui lòng
								tạo mật khẩu mới cho tài khoản <strong>${username}</strong>
							</div>

							<form action="forgot-password" method="post" id="resetPasswordForm">
								<input type="hidden" name="step" value="3"> 
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
										<span class="input-group-text"> <i class="fa fa-lock"></i>
										</span> <input type="password" placeholder="Nhập lại mật khẩu mới"
											name="confirmPassword" class="form-control" required
											id="confirmPassword">
										<button type="button" class="btn btn-outline-secondary"
											onclick="togglePassword('confirmPassword', this)">
											<i class="fa fa-eye"></i>
										</button>
									</div>
								</div>

								<!-- Yêu cầu mật khẩu -->
								<div class="password-requirements mb-3">
									<small>Yêu cầu mật khẩu:</small>
									<div class="requirement" id="req-length">
										<i class="fa fa-circle-xmark text-danger"></i> Từ 6-20 ký tự
									</div>
									<div class="requirement" id="req-upper">
										<i class="fa fa-circle-xmark text-danger"></i> Ít nhất 1 chữ hoa
									</div>
									<div class="requirement" id="req-lower">
										<i class="fa fa-circle-xmark text-danger"></i> Ít nhất 1 chữ thường
									</div>
									<div class="requirement" id="req-digit">
										<i class="fa fa-circle-xmark text-danger"></i> Ít nhất 1 chữ số
									</div>
									<div class="requirement" id="req-special">
										<i class="fa fa-circle-xmark text-danger"></i> Ít nhất 1 ký tự đặc biệt (@#$%&*!?)
									</div>
									<div class="requirement" id="req-match">
										<i class="fa fa-circle-xmark text-danger"></i> Hai mật khẩu khớp nhau
									</div>
								</div>

								<button type="submit" class="btn btn-success w-100 mb-3" id="submitBtn" disabled>
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

		// Kiểm tra độ mạnh mật khẩu realtime
		document.addEventListener('DOMContentLoaded', function() {
			const newPasswordInput = document.getElementById('newPassword');
			const confirmPasswordInput = document.getElementById('confirmPassword');
			const submitBtn = document.getElementById('submitBtn');

			if (newPasswordInput && confirmPasswordInput) {
				function validatePassword() {
					const password = newPasswordInput.value;
					const confirmPassword = confirmPasswordInput.value;

					// Kiểm tra các yêu cầu
					const requirements = {
						'req-length': password.length >= 6 && password.length <= 20,
						'req-upper': /[A-Z]/.test(password),
						'req-lower': /[a-z]/.test(password),
						'req-digit': /[0-9]/.test(password),
						'req-special': /[@#$%&*!?]/.test(password),
						'req-match': password.length > 0 && password === confirmPassword
					};

					// Cập nhật UI
					for (const [reqId, isValid] of Object.entries(requirements)) {
						const reqElement = document.getElementById(reqId);
						if (reqElement) {
							const icon = reqElement.querySelector('i');
							if (isValid) {
								reqElement.classList.add('valid');
								reqElement.classList.remove('invalid');
								icon.className = 'fa fa-circle-check text-success';
							} else {
								reqElement.classList.add('invalid');
								reqElement.classList.remove('valid');
								icon.className = 'fa fa-circle-xmark text-danger';
							}
						}
					}

					// Enable/disable submit button
					const allValid = Object.values(requirements).every(req => req);
					submitBtn.disabled = !allValid;
				}

				newPasswordInput.addEventListener('input', validatePassword);
				confirmPasswordInput.addEventListener('input', validatePassword);
			}
		});
	</script>

</body>
</html>