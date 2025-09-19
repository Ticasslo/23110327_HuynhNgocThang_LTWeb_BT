package vn.ngocthang.services;

import vn.ngocthang.entity.Video;
import java.util.List;
import java.util.Optional;

public interface VideoService {
    List<Video> findAll();
    Optional<Video> findById(Integer id);
    Video save(Video video);
    void deleteById(Integer id);
    List<Video> findByTitleContainingIgnoreCase(String title);
    List<Video> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
    List<Video> findByCategoryId(Integer categoryId);
    List<Video> findTop4ByOrderByIdDesc();
    List<Video> findTop4ByOrderByViewsDesc();
    long count();
    Long sumViews();
}
