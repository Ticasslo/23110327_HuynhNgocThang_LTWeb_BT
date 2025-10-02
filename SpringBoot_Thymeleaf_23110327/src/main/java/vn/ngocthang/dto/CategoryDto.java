package vn.ngocthang.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {
    
    private Integer id;
    
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(min = 2, max = 255, message = "Tên danh mục phải từ 2-255 ký tự")
    private String categoryName;
    
    @Size(max = 2000, message = "Mô tả hình ảnh không được quá 2000 ký tự")
    private String images;
}
