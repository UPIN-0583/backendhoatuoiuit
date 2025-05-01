package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.ProductDTO;
import com.example.backendhoatuoiuit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/filter")
    public Page<ProductDTO> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer occasionId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean isFeatured,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        return productService.filterProducts(name, categoryId, occasionId, minPrice, maxPrice, isFeatured, isActive, page, size, sort);
    }

    @GetMapping("/featured")
    public List<ProductDTO> getFeaturedProducts() {
        return productService.getFeaturedProducts();
    }

    @GetMapping("/active")
    public List<ProductDTO> getActiveProducts() {
        return productService.getActiveProducts();
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getProductsByCategory(@PathVariable Integer categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/top-sellers")
    public List<ProductDTO> getTopSellers(@RequestParam(defaultValue = "10") int limit) {
        return productService.getTopSellingProducts(limit);
    }

    @GetMapping("/{id}/related")
    public List<ProductDTO> getRelatedProducts(@PathVariable Integer id) {
        return productService.getRelatedProducts(id);
    }

    @GetMapping("/most-discounted")
    public List<ProductDTO> getMostDiscountedProducts(@RequestParam(defaultValue = "10") int limit) {
        return productService.getMostDiscountedProducts(limit);
    }


}
