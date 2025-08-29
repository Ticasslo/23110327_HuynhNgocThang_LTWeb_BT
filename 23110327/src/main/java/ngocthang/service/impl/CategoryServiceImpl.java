package ngocthang.service.impl;

import java.io.File;
import java.util.List;

import ngocthang.dao.CategoryDao;
import ngocthang.dao.impl.CategoryDaoImpl;
import ngocthang.models.Category;
import ngocthang.service.CategoryService;
import ngocthang.utils.Constant;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public void insert(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void edit(Category newCategory) {
        Category oldCategory = categoryDao.get(newCategory.getId());
        oldCategory.setName(newCategory.getName());
        
        if (newCategory.getIcon() != null) {
            // Xóa ảnh cũ đi
            String fileName = oldCategory.getIcon();
            final String dir = Constant.DIR;
            File file = new File(dir + "/category/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            oldCategory.setIcon(newCategory.getIcon());
        }
        categoryDao.edit(oldCategory);
    }

    @Override
    public void delete(int id) {
        categoryDao.delete(id);
    }

    @Override
    public Category get(int id) {
        return categoryDao.get(id);
    }

    @Override
    public Category get(String name) {
        return categoryDao.get(name);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public List<Category> search(String catename) {
        return categoryDao.search(catename);
    }
}