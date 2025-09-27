<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="container-fluid">
  <div class="mb-3">
    <a href="${pageContext.request.contextPath}/booklist" class="btn btn-outline-secondary">
      <i class="bi bi-arrow-left"></i> Quay lại danh sách
    </a>
  </div>

  <!-- Thông tin sách -->
  <div class="border rounded p-4 mb-4">
    <div class="row g-4">
      <div class="col-md-4">
        <c:choose>
          <c:when test="${book.coverImage != null}">
            <img src="${pageContext.request.contextPath}/uploads_23110327/${book.coverImage}" 
                 alt="Ảnh bìa" class="img-fluid rounded" style="max-height: 300px; object-fit: cover;">
          </c:when>
          <c:otherwise>
            <div class="ratio ratio-3x4 border bg-light d-flex align-items-center justify-content-center text-muted">
              <small>[cover_image]</small>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="col-md-8">
        <div><strong>Tiêu đề:</strong> ${book.title}</div>
        <div><strong>Mã isbn:</strong> ${book.isbn}</div>
        <div><strong>Tác giả:</strong> 
          <c:choose>
            <c:when test="${not empty bookData}">
              <c:set var="firstItem" value="${bookData[0]}" />
              <em>${firstItem[8] != null ? firstItem[8] : 'Chưa có'}</em>
            </c:when>
            <c:otherwise>
              <em>Chưa có</em>
            </c:otherwise>
          </c:choose>
        </div>
        <div><strong>Publisher:</strong> ${book.publisher}</div>
        <div><strong>Publisher_date:</strong> ${book.publishDate}</div>
        <div><strong>Quantity:</strong> ${book.quantity}</div>
        <div><strong>Reviews (</strong>
          <c:choose>
            <c:when test="${not empty bookData}">
              <c:set var="reviewCount" value="0" />
              <c:forEach var="item" items="${bookData}">
                <c:if test="${item[9] != null}">
                  <c:set var="reviewCount" value="${reviewCount + 1}" />
                </c:if>
              </c:forEach>
              ${reviewCount}
            </c:when>
            <c:otherwise>0</c:otherwise>
          </c:choose>
          <strong>)</strong>
        </div>
      </div>
    </div>
  </div>

  <!-- Phần Reviews -->
  <div class="border rounded p-4">
    <h4 class="mb-3">Reviews</h4>
    
    <c:choose>
      <c:when test="${not empty bookData}">
        <c:forEach var="item" items="${bookData}">
          <c:if test="${item[9] != null}">
            <div class="mb-3 p-3 bg-light rounded">
              <div><strong>${item[9] != null ? item[9] : 'Anonymous'}:</strong> ${item[11] != null ? item[11] : 'Không có đánh giá'}</div>
              <div class="text-muted small">Đánh giá: ${item[10]}/5 sao</div>
            </div>
          </c:if>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="text-muted">Chưa có đánh giá nào.</div>
      </c:otherwise>
    </c:choose>

    <hr class="my-4">
    
    <div class="mt-4 p-3 bg-light rounded">
      <h5 class="mb-3"><i class="bi bi-star"></i> Đánh giá sách này</h5>
      <form action="${pageContext.request.contextPath}/bookdetail" method="post">
        <input type="hidden" name="id" value="${book.bookId}" />
        <div class="row g-3">
          <div class="col-md-4">
            <label for="rating" class="form-label">Đánh giá sao: <span class="text-danger">*</span></label>
            <select class="form-select" id="rating" name="rating" required>
              <option value="">-- Chọn sao --</option>
              <option value="5">5 sao - Tuyệt vời</option>
              <option value="4">4 sao - Rất tốt</option>
              <option value="3">3 sao - Tốt</option>
              <option value="2">2 sao - Bình thường</option>
              <option value="1">1 sao - Không hài lòng</option>
            </select>
          </div>
          <div class="col-md-8">
            <label for="reviewText" class="form-label">Nhận xét chi tiết:</label>
            <textarea class="form-control" id="reviewText" name="reviewText" rows="3" 
                      placeholder="Chia sẻ cảm nhận của bạn về cuốn sách này..."></textarea>
          </div>
        </div>
        <div class="mt-3">
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>
