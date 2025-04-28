package com.example.backendhoatuoiuit.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
}
