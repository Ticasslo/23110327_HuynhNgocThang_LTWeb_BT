package vn.ngocthang.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {
    
    private Integer id;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 255, message = "Tên sản phẩm phải từ 2-255 ký tự")
    private String productName;
    
    @Size(max = 2000, message = "Mô tả không được quá 2000 ký tự")
    private String description;
    
    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @DecimalMax(value = "999999999.99", message = "Giá không được quá 999,999,999.99")
    private BigDecimal price;
    
    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    @Max(value = 999999, message = "Số lượng tồn kho không được quá 999,999")
    private Integer stock;
    
    @Min(value = 0, message = "Số lượt mua không được âm")
    @Max(value = 999999, message = "Số lượt mua không được quá 999,999")
    private Long purchases;
    
    @NotNull(message = "Danh mục không được để trống")
    private Integer categoryId;
    
    private Integer userId;
    
    private String image;
}
