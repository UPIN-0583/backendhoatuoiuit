package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.WishlistDTO;
import com.example.backendhoatuoiuit.dto.WishlistItemDTO;
import com.example.backendhoatuoiuit.entity.*;
import com.example.backendhoatuoiuit.mapper.WishlistMapper;
import com.example.backendhoatuoiuit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishlistMapper wishlistMapper;

    public WishlistDTO getWishlistByCustomerId(Integer customerId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    Customer customer = new Customer();
                    customer.setId(customerId);
                    newWishlist.setCustomer(customer);
                    return wishlistRepository.save(newWishlist);
                });

        return wishlistMapper.toDTO(wishlist);
    }

    public WishlistItemDTO addItemToWishlist(WishlistItemDTO itemDTO) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(itemDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        if (wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), itemDTO.getProductId()).isPresent()) {
            throw new RuntimeException("Item already exists in wishlist");
        }

        Product product = productRepository.findById(itemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        WishlistItem item = new WishlistItem();
        item.setWishlist(wishlist);
        item.setProduct(product);

        WishlistItem saved = wishlistItemRepository.save(item);
        return wishlistMapper.toItemDTO(saved);
    }

    public void removeItemFromWishlist(Integer itemId) {
        wishlistItemRepository.deleteById(itemId);
    }
}
