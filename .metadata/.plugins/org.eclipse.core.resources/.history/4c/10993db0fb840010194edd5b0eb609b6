<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chỉnh sửa danh mục</title>
<style>
body {
	font-family: Arial, sans-serif;
	max-width: 600px;
	margin: 50px auto;
	padding: 20px;
	background-color: #f5f5f5;
}

.container {
	background-color: white;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
	color: #333;
	text-align: center;
	margin-bottom: 30px;
}

.form-group {
	margin-bottom: 20px;
}

label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
	color: #333;
}

input[type="text"], input[type="file"] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
	font-size: 14px;
	box-sizing: border-box;
}

input[type="text"]:focus, input[type="file"]:focus {
	border-color: #007bff;
	outline: none;
	box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
}

.current-image {
	margin: 10px 0;
	text-align: center;
}

.current-image img {
	max-width: 300px;
	max-height: 300px;
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 5px;
	background-color: #f8f9fa;
}

.btn {
	padding: 12px 20px;
	margin: 5px;
	border: none;
	cursor: pointer;
	border-radius: 4px;
	font-size: 14px;
	text-decoration: none;
	display: inline-block;
	transition: background-color 0.3s;
}

.btn-primary {
	background-color: #007bff;
	color: white;
}

.btn-primary:hover {
	background-color: #0056b3;
}

.btn-secondary {
	background-color: #6c757d;
	color: white;
}

.btn-secondary:hover {
	background-color: #545b62;
}

.btn-success {
	background-color: #28a745;
	color: white;
}

.btn-success:hover {
	background-color: #1e7e34;
}

.button-group {
	text-align: center;
	margin-top: 30px;
}

.note {
	font-size: 12px;
	color: #666;
	font-style: italic;
	margin-top: 5px;
}
</style>
</head>
<body>
	<div class="container">
		<h1>✏️ Chỉnh sửa danh mục</h1>

		<form role="form" action="edit" method="post"
			enctype="multipart/form-data">
			<!-- Hidden field để gửi ID của category -->
			<input type="hidden" name="id" value="${category.id}" />

			<div class="form-group">
				<label for="categoryName">Tên danh mục:</label> <input type="text"
					id="categoryName" class="form-control"
					placeholder="Nhập tên danh mục" name="name" value="${category.name}"
					required />
			</div>

			<div class="form-group">
				<!-- Hiển thị ảnh hiện tại nếu có -->
				<c:if test="${category.icon != null && category.icon != ''}">
					<div class="current-image">
						<p>
							<strong>Ảnh hiện tại:</strong>
						</p>
						<c:url value="/image?fname=${category.icon}" var="imgUrl"></c:url>
						<img src="${imgUrl}" alt="Current Category Image" />
						<p class="note">Ảnh hiện tại: ${category.icon}</p>
					</div>
				</c:if>

				<input type="file" id="categoryIcon" name="icon" accept="image/*" />
				<p class="note">Để trống nếu không muốn thay đổi ảnh</p>

				<!-- Hidden field để giữ ảnh cũ -->
				<input type="hidden" name="oldIcon" value="${category.icon}" />
			</div>

			<div class="button-group">
				<button type="submit" class="btn btn-success">💾 Lưu thay
					đổi</button>
				<button type="reset" class="btn btn-secondary">🔄 Reset</button>
				<a href="<c:url value='/admin/category/list'/>"
					class="btn btn-secondary">🔙 Quay lại</a>
			</div>
		</form>
	</div>
</body>
</html>