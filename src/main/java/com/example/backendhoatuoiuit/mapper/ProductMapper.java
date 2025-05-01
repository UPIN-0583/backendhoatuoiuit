package com.example.backendhoatuoiuit.mapper;

import com.example.backendhoatuoiuit.dto.ProductDTO;
import com.example.backendhoatuoiuit.entity.Category;
import com.example.backendhoatuoiuit.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setIsActive(product.getIsActive());
        dto.setIsFeatured(product.getIsFeatured());
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            product.setCategory(category);
        }
        dto.setIsActive(product.getIsActive());
        product.setIsFeatured(dto.getIsFeatured());
        return product;
    }
}
