package ngocthang.dao;

import java.util.List;
import ngocthang.entity.Category;

public interface ICategoryDAO {
    void insert(Category category);
    void update(Category category);
    void delete(int id) throws Exception;
    Category findById(int id);
    List<Category> findAll();
    List<Category> findByName(String categoryname);
}