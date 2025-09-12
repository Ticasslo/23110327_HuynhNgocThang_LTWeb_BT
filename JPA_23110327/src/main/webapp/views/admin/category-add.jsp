<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm danh mục</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	min-height: 100vh;
	padding: 20px;
}

.container {
	max-width: 600px;
	margin: 50px auto;
	background: white;
	padding: 40px;
	border-radius: 15px;
	box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
	backdrop-filter: blur(10px);
}

h1 {
	color: #333;
	text-align: center;
	margin-bottom: 40px;
	font-size: 2.2em;
	font-weight: 300;
}

.form-group {
	margin-bottom: 25px;
}

label {
	display: block;
	margin-bottom: 8px;
	font-weight: 600;
	color: #495057;
	font-size: 14px;
	text-transform: uppercase;
	letter-spacing: 0.5px;
}

input[type="text"], input[type="file"] {
	width: 100%;
	padding: 15px;
	border: 2px solid #e9ecef;
	border-radius: 8px;
	font-size: 16px;
	transition: all 0.3s ease;
	background-color: #f8f9fa;
}

input[type="text"]:focus, input[type="file"]:focus {
	border-color: #007bff;
	outline: none;
	box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.1);
	background-color: white;
}

input[type="text"]::placeholder {
	color: #adb5bd;
	font-style: italic;
}

.file-input-wrapper {
	position: relative;
	overflow: hidden;
	display: inline-block;
	width: 100%;
}

.file-input-label {
	display: block;
	padding: 15px;
	background: linear-gradient(135deg, #6c757d 0%, #495057 100%);
	color: white;
	text-align: center;
	border-radius: 8px;
	cursor: pointer;
	transition: all 0.3s ease;
	font-weight: 500;
}

.file-input-label:hover {
	background: linear-gradient(135deg, #495057 0%, #343a40 100%);
	transform: translateY(-1px);
}

input[type="file"] {
	position: absolute;
	left: -9999px;
}

.btn {
	padding: 15px 25px;
	margin: 8px;
	border: none;
	cursor: pointer;
	border-radius: 8px;
	font-size: 16px;
	font-weight: 500;
	text-decoration: none;
	display: inline-block;
	transition: all 0.3s ease;
	text-transform: uppercase;
	letter-spacing: 0.5px;
}

.btn-primary {
	background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
	color: white;
	box-shadow: 0 4px 15px rgba(40, 167, 69, 0.3);
}

.btn-primary:hover {
	background: linear-gradient(135deg, #1e7e34 0%, #17a2b8 100%);
	transform: translateY(-2px);
	box-shadow: 0 8px 25px rgba(40, 167, 69, 0.4);
}

.btn-secondary {
	background: linear-gradient(135deg, #6c757d 0%, #495057 100%);
	color: white;
	box-shadow: 0 4px 15px rgba(108, 117, 125, 0.3);
}

.btn-secondary:hover {
	background: linear-gradient(135deg, #495057 0%, #343a40 100%);
	transform: translateY(-2px);
	box-shadow: 0 8px 25px rgba(108, 117, 125, 0.4);
}

.button-group {
	text-align: center;
	margin-top: 40px;
	padding-top: 30px;
	border-top: 1px solid #e9ecef;
}

.form-info {
	background: linear-gradient(135deg, #cce5ff 0%, #e6f3ff 100%);
	padding: 15px;
	border-radius: 8px;
	margin-bottom: 30px;
	border-left: 4px solid #007bff;
}

.form-info p {
	color: #495057;
	font-size: 14px;
	margin: 0;
}

@media ( max-width : 768px) {
	.container {
		margin: 20px auto;
		padding: 25px;
	}
	h1 {
		font-size: 1.8em;
	}
	.btn {
		display: block;
		width: 100%;
		margin: 10px 0;
	}
}
</style>
</head>
<body>
	<div class="container">
		<h1>➕ Thêm danh mục mới</h1>

		<!-- Hiển thị lỗi -->
		<c:if test="${not empty error}">
			<div class="alert alert-danger" style="background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #f5c6cb;">
				<strong>❌ Lỗi:</strong> ${error}
			</div>
		</c:if>

		<form role="form" action="add" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="categoryName">📝 Tên danh mục:</label> <input
					type="text" id="categoryName" class="form-control"
					placeholder="......" name="name"
					required maxlength="100" />
			</div>

			<div class="form-group">
				<label for="categoryIcon">🖼️ Ảnh đại diện:</label>
				<div class="file-input-wrapper">
					<input type="file" id="categoryIcon" name="icon" accept="image/*"
						onchange="updateFileName(this)" /> <label for="categoryIcon"
						class="file-input-label" id="fileLabel"> 📁 Chọn hình ảnh
						(PNG, JPG, GIF...) </label>
				</div>
			</div>

			<div class="button-group">
				<button type="submit" class="btn btn-primary">💾 Thêm danh
					mục</button>
				<button type="reset" class="btn btn-secondary"
					onclick="resetFileLabel()">🔄 Reset</button>
				<c:choose>
					<c:when test="${currentUser.roleid == 1}">
						<a href="<c:url value='/admin/category/list'/>"
							class="btn btn-secondary">🔙 Quay lại danh sách</a>
					</c:when>
					<c:when test="${currentUser.roleid == 2}">
						<a href="<c:url value='/manager/category/list'/>"
							class="btn btn-secondary">🔙 Quay lại danh sách</a>
					</c:when>
					<c:otherwise>
						<a href="<c:url value='/user/home'/>"
							class="btn btn-secondary">🔙 Quay lại trang chủ</a>
					</c:otherwise>
				</c:choose>
			</div>
		</form>
	</div>

	<script>
		function updateFileName(input) {
			const label = document.getElementById('fileLabel');
			if (input.files && input.files[0]) {
				label.textContent = '📁 Đã chọn: ' + input.files[0].name;
				label.style.background = 'linear-gradient(135deg, #28a745 0%, #20c997 100%)';
			}
		}

		function resetFileLabel() {
			const label = document.getElementById('fileLabel');
			label.textContent = '📁 Chọn hình ảnh (PNG, JPG, GIF...)';
			label.style.background = 'linear-gradient(135deg, #6c757d 0%, #495057 100%)';
		}

		// Form validation
		document.querySelector('form').addEventListener('submit', function(e) {
			const nameInput = document.getElementById('categoryName');
			if (nameInput.value.trim().length < 2) {
				alert('⚠️ Tên danh mục phải có ít nhất 2 ký tự!');
				e.preventDefault();
				nameInput.focus();
			}
		});
	</script>
</body>
</html>