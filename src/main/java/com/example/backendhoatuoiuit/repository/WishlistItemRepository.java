package com.example.backendhoatuoiuit.repository;

import com.example.backendhoatuoiuit.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {
    Optional<WishlistItem> findByWishlistIdAndProductId(Integer wishlistId, Integer productId);
    void deleteById(Integer id);
}
