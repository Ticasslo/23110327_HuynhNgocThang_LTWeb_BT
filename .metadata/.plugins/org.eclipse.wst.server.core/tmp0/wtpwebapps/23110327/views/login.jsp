<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>

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
				<form action="login" method="post">
					<h2 class="text-center mb-4">Đăng nhập</h2>
					<c:if test="${alert != null}">
						<div class="alert alert-danger">${alert}</div>
					</c:if>

					<div class="mb-3">
						<label class="form-label">Tài khoản</label>
						<div class="input-group">
							<span class="input-group-text"> <i class="fa fa-user"></i>
							</span> <input type="text" placeholder="Nhập tài khoản" name="username"
								class="form-control" required>
						</div>
					</div>

					<div class="mb-3">
						<label class="form-label">Mật khẩu</label>
						<div class="input-group">
							<span class="input-group-text"> <i class="fa fa-lock"></i>
							</span> <input type="password" placeholder="Nhập mật khẩu"
								name="password" class="form-control" required>
						</div>
					</div>

					<div class="mb-3 form-check">
						<input type="checkbox" class="form-check-input" name="remember">
						<label class="form-check-label">Ghi nhớ đăng nhập</label>
					</div>

					<button type="submit" class="btn btn-primary w-100">Đăng
						nhập</button>

					<div class="text-center mt-3">
						<p>
							<a href="${pageContext.request.contextPath}/forgot-password">Quên
								mật khẩu?</a>
						</p>
						<p>
							Chưa có tài khoản? <a
								href="${pageContext.request.contextPath}/register">Đăng ký
								ngay</a>
						</p>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>