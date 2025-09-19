<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${not empty sessionScope.account && sessionScope.account.roleid == 2}">
    <meta name="decorator" content="user.jsp" />
    <c:set var="userMode" value="true"/>
    <c:set var="searchAction" value="/user/search"/>
    <c:set var="quickLinks" value="/user"/>
    
</c:if>
<c:if test="${empty sessionScope.account || sessionScope.account.roleid != 2}">
    <c:set var="searchAction" value="/search"/>
    <c:set var="quickLinks" value="/"/>
</c:if>

<style>
    .search-container {
        min-height: 60vh;
        padding: 40px 0;
    }
    .video-card {
        transition: transform 0.3s ease;
        cursor: pointer;
    }
    .video-card:hover {
        transform: translateY(-5px);
    }
    .video-thumbnail {
        height: 200px;
        background: linear-gradient(45deg, #667eea 0%, #764ba2 100%);
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        font-size: 3rem;
    }
</style>

<div class="search-container">
    <div class="container">
        <!-- Search Form -->
        <div class="row justify-content-center mb-5">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-body p-4">
                        <h2 class="text-center mb-4">
                            <i class="bi bi-search"></i> Tìm kiếm video
                        </h2>
                        <form method="get" action="${searchAction}">
                            <div class="input-group input-group-lg">
                                <input type="text" class="form-control" name="keyword" 
                                       placeholder="Nhập từ khóa tìm kiếm..." 
                                       value="${param.keyword}">
                                <button class="btn btn-primary" type="submit">
                                    <i class="bi bi-search"></i> Tìm kiếm
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Search Results -->
        <c:if test="${not empty param.keyword}">
            <div class="row">
                <div class="col-12">
                    <h4 class="mb-4">
                        <i class="bi bi-funnel"></i> Kết quả tìm kiếm cho: "${param.keyword}"
                        <span class="badge bg-primary ms-2">${empty videos ? 0 : videos.size()} video</span>
                    </h4>
                </div>
            </div>

            <c:choose>
                <c:when test="${not empty videos}">
                    <div class="row">
                        <c:forEach var="video" items="${videos}">
                            <div class="col-md-3 mb-4">
                                <div class="card video-card h-100">
                                    <div class="video-thumbnail">
                                        <c:choose>
                                            <c:when test="${not empty video.poster}">
                                                <img src="/image?fname=${video.poster}" alt="${video.title}"
                                                     style="width:100%; height:100%; object-fit:cover; border-top-left-radius:.375rem; border-top-right-radius:.375rem;" />
                                            </c:when>
                                            <c:otherwise>
                                                <i class="bi bi-play-circle"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="card-body">
                                        <h6 class="card-title">${video.title}</h6>
                                        <p class="card-text text-muted small">${video.description}</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <small class="text-muted">
                                                <i class="bi bi-eye"></i> ${video.views} lượt xem
                                            </small>
                                            <small class="text-primary">${video.category.categoryName}</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center py-5">
                        <i class="bi bi-search display-1 text-muted"></i>
                        <h4 class="text-muted mt-3">Không tìm thấy video nào</h4>
                        <p class="text-muted">Hãy thử từ khóa khác hoặc kiểm tra chính tả</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>

        <!-- Popular Categories -->
        <div class="row mt-5">
            <div class="col-12">
                <h4 class="mb-4">
                    <i class="bi bi-tags"></i> Danh mục phổ biến
                </h4>
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <div class="card text-center">
                            <div class="card-body">
                                <i class="bi bi-book display-4 text-primary mb-3"></i>
                                <h6>Giáo dục</h6>
                                <small class="text-muted">Học tập và phát triển</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <div class="card text-center">
                            <div class="card-body">
                                <i class="bi bi-film display-4 text-success mb-3"></i>
                                <h6>Giải trí</h6>
                                <small class="text-muted">Vui nhộn và thú vị</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <div class="card text-center">
                            <div class="card-body">
                                <i class="bi bi-trophy display-4 text-warning mb-3"></i>
                                <h6>Thể thao</h6>
                                <small class="text-muted">Thể thao và vận động</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <div class="card text-center">
                            <div class="card-body">
                                <i class="bi bi-music-note display-4 text-info mb-3"></i>
                                <h6>Âm nhạc</h6>
                                <small class="text-muted">Nhạc và ca hát</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>