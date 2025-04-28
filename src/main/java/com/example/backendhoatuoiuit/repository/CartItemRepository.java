package com.example.backendhoatuoiuit.repository;

import com.example.backendhoatuoiuit.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
