package ngocthang.services.impl;

import java.io.File;
import java.util.List;

import ngocthang.dao.ICategoryDAO;
import ngocthang.dao.impl.CategoryDAOImpl;
import ngocthang.entity.Category;
import ngocthang.services.ICategoryService;
import ngocthang.utils.Constant;

public class CategoryServiceImpl implements ICategoryService {
    ICategoryDAO categoryDao = new CategoryDAOImpl();

    @Override
    public void insert(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void edit(Category newCategory) {
        Category oldCategory = categoryDao.get(newCategory.getId());
        if (oldCategory == null) {
            throw new RuntimeException("Category not found with id: " + newCategory.getId());
        }
        
        oldCategory.setName(newCategory.getName());
        oldCategory.setUserid(newCategory.getUserid());
        
        if (newCategory.getIcon() != null) {
            // Xóa ảnh cũ đi (nếu có)
            String fileName = oldCategory.getIcon();
            if (fileName != null && !fileName.trim().isEmpty()) {
                final String dir = Constant.DIR;
                File file = new File(dir + "/category/" + fileName);
                if (file.exists()) {
                    file.delete();
                }
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

    @Override
    public List<Category> getCategoriesByUserId(int userid) {
        return categoryDao.getCategoriesByUserId(userid);
    }
}