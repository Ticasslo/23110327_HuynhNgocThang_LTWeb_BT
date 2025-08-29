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
								<input type="hidden" name="step" value="2"> <input
									type="hidden" name="username" value="${username}">

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
									<i class="fa fa-key"></i> Đặt lại mật khẩu
								</button>

								<a href="forgot-password" class="btn btn-secondary w-100"> <i
									class="fa fa-arrow-left"></i> Quay lại
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

</body>
</html>