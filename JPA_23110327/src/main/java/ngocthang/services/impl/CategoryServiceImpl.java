package ngocthang.services.impl;

import java.util.List;
import ngocthang.dao.ICategoryDAO;
import ngocthang.dao.impl.CategoryDAOImpl;
import ngocthang.entity.Category;
import ngocthang.services.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {
    ICategoryDAO categoryDao = new CategoryDAOImpl();

    @Override
    public void insert(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category category) {
        Category cate = this.findById(category.getId());
        if (cate != null) {
            categoryDao.update(category);
        }
    }

    @Override
    public void delete(int id) throws Exception {
        Category cate = this.findById(id);
        if (cate != null) {
            categoryDao.delete(id);
        } else {
            throw new Exception("Category không tồn tại");
        }
    }

    @Override
    public Category findById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category> findByName(String categoryname) {
        return categoryDao.findByName(categoryname);
    }
}