<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<sitemesh:write property="head">
<style>
.container { background: linear-gradient(180deg,#eef4ff 0%,#e4edff 100%); padding: 30px; border-radius: 16px; box-shadow: 0 10px 30px rgba(31,38,135,.15); border: 1px solid #d5e0ff; max-width: 1200px; margin: 30px auto; }

h1 { color: #333; text-align: center; margin-bottom: 30px; font-size: 2.2em; font-weight: 300; }
.form-group { margin-bottom: 20px; }
label { display: block; margin-bottom: 5px; font-weight: bold; color: #333; }
input[type="text"], input[type="file"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; box-sizing: border-box; }
input[type="text"]:focus, input[type="file"]:focus { border-color: #007bff; outline: none; box-shadow: 0 0 5px rgba(0,123,255,.3); }
.current-image { margin: 10px 0; text-align: center; }
.current-image img { max-width: 300px; max-height: 300px; border: 1px solid #ddd; border-radius: 4px; padding: 5px; background-color: #f8f9fa; }
.btn { padding: 12px 20px; margin: 5px; border: none; cursor: pointer; border-radius: 4px; font-size: 14px; text-decoration: none; display: inline-block; transition: background-color .3s; }
.btn-primary { background-color: #007bff; color: #fff; }
.btn-primary:hover { background-color: #0056b3; }
.btn-secondary { background-color: #6c757d; color: #fff; }
.btn-secondary:hover { background-color: #545b62; }
.btn-success { background-color: #28a745; color: #fff; }
.btn-success:hover { background-color: #1e7e34; }
.button-group { text-align: center; margin-top: 30px; }
.note { font-size: 12px; color: #666; font-style: italic; margin-top: 5px; }
</style>
</sitemesh:write>

<div class="container">
	<h1>✏️ Chỉnh sửa danh mục</h1>

	<c:if test="${not empty error}">
		<div class="alert alert-danger" style="background-color:#f8d7da;color:#721c24;padding:15px;border-radius:5px;margin-bottom:20px;border:1px solid #f5c6cb;">
			<strong>❌ Lỗi:</strong> ${error}
		</div>
	</c:if>

	<form role="form" action="edit" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${category.id}" />

		<div class="form-group">
			<label for="categoryName">Tên danh mục:</label>
			<input type="text" id="categoryName" class="form-control" placeholder="Nhập tên danh mục" name="name" value="${category.name}" required />
		</div>

		<div class="form-group">
			<c:if test="${category.icon != null && category.icon != ''}">
				<div class="current-image">
					<p><strong>Ảnh hiện tại:</strong></p>
					<c:url value="/image?fname=${category.icon}" var="imgUrl" />
					<img src="${imgUrl}" alt="Current Category Image" />
					<p class="note">Ảnh hiện tại: ${category.icon}</p>
				</div>
			</c:if>
			<input type="file" id="categoryIcon" name="icon" accept="image/*" />
			<p class="note">Để trống nếu không muốn thay đổi ảnh</p>
			<input type="hidden" name="oldIcon" value="${category.icon}" />
		</div>

		<div class="button-group">
			<button type="submit" class="btn btn-success">💾 Lưu thay đổi</button>
			<button type="reset" class="btn btn-secondary">🔄 Reset</button>
			<c:choose>
				<c:when test="${currentUser.roleid == 1}">
					<a href="<c:url value='/admin/category/list'/>" class="btn btn-secondary">🔙 Quay lại danh sách</a>
				</c:when>
				<c:when test="${currentUser.roleid == 2}">
					<a href="<c:url value='/manager/category/list'/>" class="btn btn-secondary">🔙 Quay lại danh sách</a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value='/user/home'/>" class="btn btn-secondary">🔙 Quay lại trang chủ</a>
				</c:otherwise>
			</c:choose>
		</div>
	</form>
</div>