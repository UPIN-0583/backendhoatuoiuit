package com.example.backendhoatuoiuit.mapper;

import com.example.backendhoatuoiuit.dto.ProductDTO;
import com.example.backendhoatuoiuit.dto.ProductViewDTO;
import com.example.backendhoatuoiuit.dto.PromotionDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductViewMapper {

    public ProductViewDTO toProductViewDTO(ProductDTO product, PromotionDTO promotion, Double averageRating) {
        if (product == null) return null;

        BigDecimal discountValue = (promotion != null && promotion.getDiscountValue() != null)
                ? promotion.getDiscountValue()
                : BigDecimal.ZERO;

        ProductViewDTO dto = new ProductViewDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscountValue(discountValue);
        dto.setFinalPrice(product.getPrice().subtract(discountValue));
        dto.setAverageRating(averageRating);
        dto.setImageUrl(product.getImageUrl());
        dto.setIsActive(product.getIsActive());
        dto.setCategoryId(product.getCategoryId());
        dto.setCategoryName(product.getCategoryName());
        dto.setFlowerNames(product.getFlowerNames());
        dto.setOccasionNames(product.getOccasionNames());
        return dto;
    }
}
