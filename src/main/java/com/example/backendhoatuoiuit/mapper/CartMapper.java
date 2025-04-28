package com.example.backendhoatuoiuit.mapper;

import com.example.backendhoatuoiuit.dto.CartDTO;
import com.example.backendhoatuoiuit.dto.CartItemDTO;
import com.example.backendhoatuoiuit.entity.Cart;
import com.example.backendhoatuoiuit.entity.CartItem;
import com.example.backendhoatuoiuit.entity.Customer;
import com.example.backendhoatuoiuit.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setCustomerId(cart.getCustomer() != null ? cart.getCustomer().getId() : null);
        if (cart.getItems() != null) {
            dto.setItems(cart.getItems().stream().map(this::toItemDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    public CartItemDTO toItemDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setCartId(item.getCart() != null ? item.getCart().getId() : null);
        dto.setProductId(item.getProduct() != null ? item.getProduct().getId() : null);
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
