package com.example.backendhoatuoiuit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer categoryId;
    private Boolean isActive;
    private Boolean isFeatured;
}
