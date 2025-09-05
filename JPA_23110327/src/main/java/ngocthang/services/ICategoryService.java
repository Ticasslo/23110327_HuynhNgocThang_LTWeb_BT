package ngocthang.services;

import java.util.List;
import ngocthang.entity.Category;

public interface ICategoryService {
    void insert(Category category);
    void update(Category category);
    void delete(int id) throws Exception;
    Category findById(int id);
    List<Category> findAll();
    List<Category> findByName(String categoryname);
}