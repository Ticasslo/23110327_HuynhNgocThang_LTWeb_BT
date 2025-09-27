package ngocthang.dao;

import ngocthang.entity.Rating;
import java.util.List;

public interface IRatingDAO_23110327 {
    Rating save(Rating rating);
    Rating update(Rating rating);
    void deleteByUserIdAndBookId(int userId, int bookId);
    Rating findByUserIdAndBookId(int userId, int bookId);
    List<Rating> findByBookId(int bookId);
    boolean existsByUserIdAndBookId(int userId, int bookId);
}
