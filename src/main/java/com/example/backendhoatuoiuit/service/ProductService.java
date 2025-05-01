package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.ProductDTO;
import com.example.backendhoatuoiuit.entity.Occasion;
import com.example.backendhoatuoiuit.entity.Product;
import com.example.backendhoatuoiuit.mapper.ProductMapper;
import com.example.backendhoatuoiuit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        if (productDTO.getCategoryId() != null) {
            product.getCategory().setId(productDTO.getCategoryId());
        }
        product.setIsActive(productDTO.getIsActive());
        product.setIsFeatured(productDTO.getIsFeatured());
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public Page<ProductDTO> filterProducts(String name, Integer categoryId, Integer occasionId,
                                           BigDecimal minPrice, BigDecimal maxPrice, Boolean isFeatured, Boolean isActive,
                                           int page, int size, String[] sort) {
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        Page<Product> products = productRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (occasionId != null) {
                predicates.add(cb.equal(root.join("occasions").get("id"), occasionId));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (isFeatured != null) {
                predicates.add(cb.equal(root.get("isFeatured"), isFeatured));
            }
            if (isActive != null) {
                predicates.add(cb.equal(root.get("isActive"), isActive));
            }

            query.distinct(true); // chống trùng sản phẩm khi join occasion
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return products.map(productMapper::toDTO);
    }

    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public List<ProductDTO> getActiveProducts() {
        return productRepository.findByIsActiveTrue()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public List<ProductDTO> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public List<ProductDTO> getTopSellingProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findTopSellingProducts(pageable)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public List<ProductDTO> getRelatedProducts(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Integer categoryId = product.getCategory() != null ? product.getCategory().getId() : null;

        Set<Integer> occasionIds = product.getOccasions().stream()
                .map(Occasion::getId)
                .collect(Collectors.toSet());

        return productRepository.findRelatedProducts(categoryId, occasionIds, productId)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public List<ProductDTO> getMostDiscountedProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findMostDiscountedProducts(pageable)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }



}
