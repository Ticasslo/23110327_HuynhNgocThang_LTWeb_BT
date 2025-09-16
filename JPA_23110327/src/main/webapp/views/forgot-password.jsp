<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<sitemesh:write property="head">
    <title>Quên mật khẩu</title>
    <style>
        .forgot-container {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px 0;
        }
        .forgot-card {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px;
        }
        .forgot-title {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
            font-weight: 300;
        }
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
</sitemesh:write>

<div class="forgot-container">
    <div class="forgot-card">
		<h3 class="forgot-title">
			<i class="bi bi-key"></i> Quên mật khẩu
		</h3>

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
										</span> 									<input type="text" placeholder="Nhập tên tài khoản"
											name="username" class="form-control" required
											value="${param.username}">
									</div>
									<div class="form-text">Nhập tên tài khoản hoặc email để tiếp tục</div>
								</div>

								<button type="submit" class="btn btn-primary w-100 mb-3">
									<i class="fa fa-arrow-right"></i> Tiếp tục
								</button>
							</form>
						</c:if>

						<!-- Bước 2: Xác thực email/phone -->
						<c:if test="${step == 2}">
							<div class="alert alert-info">
								<i class="fa fa-info-circle"></i> Vui lòng xác thực thông tin để tiếp tục
							</div>

							<c:if test="${error != null}">
								<div class="alert alert-danger">${error}</div>
							</c:if>

							<form action="forgot-password" method="post" id="verifyForm">
								<input type="hidden" name="step" value="2"> 
								<input type="hidden" name="username" value="${username}">

								<div class="mb-3">
									<label class="form-label">Email</label>
									<div class="input-group">
										<span class="input-group-text"> <i class="fa fa-envelope"></i>
										</span> <input type="email" placeholder="Nhập email"
											name="email" class="form-control" required>
									</div>
								</div>

								<div class="mb-3">
									<label class="form-label">Số điện thoại</label>
									<div class="input-group">
										<span class="input-group-text"> <i class="fa fa-phone"></i>
										</span> <input type="tel" placeholder="Nhập số điện thoại"
											name="phone" class="form-control" required>
									</div>
								</div>

								<button type="submit" class="btn btn-primary w-100 mb-3">
									<i class="fa fa-check"></i> Xác thực
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

							<c:if test="${error != null}">
								<div class="alert alert-danger">${error}</div>
							</c:if>

							<form action="forgot-password" method="post" id="resetPasswordForm">
								<input type="hidden" name="step" value="3"> 
								<input type="hidden" name="username" value="${username}">

								<div class="mb-3">
									<label class="form-label">Mật khẩu mới</label>
									<div class="input-group">
										<span class="input-group-text"> <i class="fa fa-lock"></i>
										</span> <input type="password" placeholder="Nhập mật khẩu mới"
											name="newPassword" class="form-control" required
											id="newPassword" onkeyup="validatePassword()">
										<button type="button" class="btn btn-outline-secondary"
											onclick="togglePassword('newPassword', this)">
											<i class="fa fa-eye"></i>
										</button>
									</div>
									<!-- Hiển thị điều kiện mật khẩu mạnh -->
									<div class="password-requirements" id="passwordRequirements">
									<div class="requirement" id="length">
										<i class="fa fa-times text-danger"></i> Ít nhất 6 ký tự
									</div>
										<div class="requirement" id="uppercase">
											<i class="fa fa-times text-danger"></i> Có ít nhất 1 chữ hoa
										</div>
										<div class="requirement" id="lowercase">
											<i class="fa fa-times text-danger"></i> Có ít nhất 1 chữ thường
										</div>
										<div class="requirement" id="number">
											<i class="fa fa-times text-danger"></i> Có ít nhất 1 số
										</div>
										<div class="requirement" id="special">
											<i class="fa fa-times text-danger"></i> Có ít nhất 1 ký tự đặc biệt
										</div>
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

<sitemesh:write property="page.scripts">
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

		// Validate mật khẩu mạnh
		function validatePassword() {
			const password = document.getElementById('newPassword').value;
			const requirements = {
				length: password.length >= 6,
				uppercase: /[A-Z]/.test(password),
				lowercase: /[a-z]/.test(password),
				number: /\d/.test(password),
				special: /[!@#$%^&*(),.?":{}|<>]/.test(password)
			};

			// Cập nhật hiển thị cho từng điều kiện
			Object.keys(requirements).forEach(key => {
				const element = document.getElementById(key);
				const icon = element.querySelector('i');
				
				if (requirements[key]) {
					element.className = 'requirement valid';
					icon.className = 'fa fa-check text-success';
				} else {
					element.className = 'requirement invalid';
					icon.className = 'fa fa-times text-danger';
				}
			});
		}
	</script>
</sitemesh:write>
