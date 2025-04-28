package com.example.backendhoatuoiuit.service;

import com.example.backendhoatuoiuit.dto.ProductDTO;
import com.example.backendhoatuoiuit.entity.Product;
import com.example.backendhoatuoiuit.entity.ProductStatus;
import com.example.backendhoatuoiuit.mapper.ProductMapper;
import com.example.backendhoatuoiuit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        product.setStatus(ProductStatus.valueOf(productDTO.getStatus()));
        product.setIsFeatured(productDTO.getIsFeatured());
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
