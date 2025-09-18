package vn.ngocthang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.ngocthang.entity.Video;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    
    // Tìm kiếm video theo title
    List<Video> findByTitleContainingIgnoreCase(String title);
    
    // Tìm kiếm video theo title hoặc description
    List<Video> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
    
    // Tìm video theo category
    List<Video> findByCategoryId(Integer categoryId);
    
    // Lấy video mới nhất
    @Query("SELECT v FROM Video v ORDER BY v.id DESC")
    List<Video> findTop4ByOrderByIdDesc();
    
    // Lấy video có lượt xem cao nhất
    @Query("SELECT v FROM Video v ORDER BY v.views DESC")
    List<Video> findTop4ByOrderByViewsDesc();
    
    // Đếm tổng số video
    long count();
    
    // Đếm tổng lượt xem
    @Query("SELECT SUM(v.views) FROM Video v")
    Long sumViews();
}
