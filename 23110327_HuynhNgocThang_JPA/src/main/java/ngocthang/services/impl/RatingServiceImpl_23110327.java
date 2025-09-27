package ngocthang.services.impl;

import ngocthang.dao.IRatingDAO_23110327;
import ngocthang.dao.impl.RatingDAOImpl_23110327;
import ngocthang.entity.Rating;
import ngocthang.services.IRatingService_23110327;
import java.util.List;

public class RatingServiceImpl_23110327 implements IRatingService_23110327 {

    private final IRatingDAO_23110327 ratingDAO_23110327 = new RatingDAOImpl_23110327();

    @Override
    public Rating save(Rating rating) {
        return ratingDAO_23110327.save(rating);
    }

    @Override
    public Rating update(Rating rating) {
        return ratingDAO_23110327.update(rating);
    }

    @Override
    public void deleteByUserIdAndBookId(int userId, int bookId) {
        ratingDAO_23110327.deleteByUserIdAndBookId(userId, bookId);
    }

    @Override
    public Rating findByUserIdAndBookId(int userId, int bookId) {
        return ratingDAO_23110327.findByUserIdAndBookId(userId, bookId);
    }

    @Override
    public List<Rating> findByBookId(int bookId) {
        return ratingDAO_23110327.findByBookId(bookId);
    }

    @Override
    public boolean existsByUserIdAndBookId(int userId, int bookId) {
        return ratingDAO_23110327.existsByUserIdAndBookId(userId, bookId);
    }
}
