package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.CartItemDTO;
import com.example.backendhoatuoiuit.entity.Cart;
import com.example.backendhoatuoiuit.entity.CartItem;
import com.example.backendhoatuoiuit.entity.Product;
import com.example.backendhoatuoiuit.repository.CartItemRepository;
import com.example.backendhoatuoiuit.repository.CartRepository;
import com.example.backendhoatuoiuit.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    public CartItemDTO addItemToCart(CartItemDTO dto) {
        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = new CartItem();
        item.setCart(cart);

        Product product = new Product();
        product.setId(dto.getProductId());
        item.setProduct(product);

        item.setQuantity(dto.getQuantity());

        item = cartItemRepository.save(item);

        CartMapper mapper = new CartMapper();
        return mapper.toItemDTO(item);
    }

    public void removeItemFromCart(Integer itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public CartItemDTO updateQuantity(Integer itemId, Integer newQuantity) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (newQuantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        item.setQuantity(newQuantity);
        item = cartItemRepository.save(item);

        CartMapper mapper = new CartMapper();
        return mapper.toItemDTO(item);
    }

    public BigDecimal calculateCartTotal(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cart.getItems().stream()
                .map(item -> {
                    BigDecimal price = item.getProduct().getPrice();
                    return price.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
