package vn.ngocthang.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @Column(name = "productName", columnDefinition = "NVARCHAR(255)")
    private String productName;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "image", columnDefinition = "NVARCHAR(500)")
    private String image;

    @Column(name = "price", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    @Column(name = "purchases", nullable = false)
    private Long purchases = 0L;

    @Column(name = "stock")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
