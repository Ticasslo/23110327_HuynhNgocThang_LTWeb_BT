package vn.ngocthang.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ngocthang.entity.Video;
import vn.ngocthang.repository.VideoRepository;
import vn.ngocthang.services.VideoService;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Optional<Video> findById(Integer id) {
        return videoRepository.findById(id);
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void deleteById(Integer id) {
        videoRepository.deleteById(id);
    }

    @Override
    public List<Video> findByTitleContainingIgnoreCase(String title) {
        return videoRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Video> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description) {
        return videoRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(title, description);
    }

    @Override
    public List<Video> findByCategoryId(Integer categoryId) {
        return videoRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Video> findTop4ByOrderByIdDesc() {
        return videoRepository.findTop4ByOrderByIdDesc();
    }

    @Override
    public List<Video> findTop4ByOrderByViewsDesc() {
        return videoRepository.findTop4ByOrderByViewsDesc();
    }

    @Override
    public long count() {
        return videoRepository.count();
    }

    @Override
    public Long sumViews() {
        return videoRepository.sumViews();
    }
}
