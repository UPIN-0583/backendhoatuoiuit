package com.example.backendhoatuoiuit.mapper;

import com.example.backendhoatuoiuit.dto.WishlistDTO;
import com.example.backendhoatuoiuit.dto.WishlistItemDTO;
import com.example.backendhoatuoiuit.entity.Wishlist;
import com.example.backendhoatuoiuit.entity.WishlistItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    public WishlistDTO toDTO(Wishlist wishlist) {
        WishlistDTO dto = new WishlistDTO();
        dto.setId(wishlist.getId());
        dto.setCustomerId(wishlist.getCustomer().getId());
        dto.setItems(wishlist.getItems().stream().map(this::toItemDTO).collect(Collectors.toList()));
        return dto;
    }

    public WishlistItemDTO toItemDTO(WishlistItem item) {
        WishlistItemDTO dto = new WishlistItemDTO();
        dto.setId(item.getId());
        dto.setWishlistId(item.getWishlist().getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setImageUrl(item.getProduct().getImageUrl());
        dto.setPrice(item.getProduct().getPrice().doubleValue());
        return dto;
    }
}
