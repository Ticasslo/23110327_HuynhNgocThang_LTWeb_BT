package ngocthang.models;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String categoryname;
    private String images;
    private boolean isEdit = false;
    
    // Constructors
    public CategoryModel() {
        super();
    }
    
    public CategoryModel(int id, String categoryname, String images) {
        this.id = id;
        this.categoryname = categoryname;
        this.images = images;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCategoryname() {
        return categoryname;
    }
    
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
    
    public String getImages() {
        return images;
    }
    
    public void setImages(String images) {
        this.images = images;
    }
    
    public boolean isEdit() {
        return isEdit;
    }
    
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
    
    @Override
    public String toString() {
        return "CategoryModel [id=" + id + ", categoryname=" + categoryname + ", images=" + images + ", isEdit=" + isEdit + "]";
    }
}