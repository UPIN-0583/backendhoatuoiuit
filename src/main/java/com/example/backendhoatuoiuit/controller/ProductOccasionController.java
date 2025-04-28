package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.entity.ProductOccasion;
import com.example.backendhoatuoiuit.service.ProductOccasionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-occasion")
public class ProductOccasionController {

    @Autowired
    private ProductOccasionService productOccasionService;

    @GetMapping("/product/{productId}")
    public List<ProductOccasion> getOccasionsByProduct(@PathVariable Integer productId) {
        return productOccasionService.getOccasionsByProductId(productId);
    }

    @PostMapping
    public ProductOccasion addOccasionToProduct(@RequestBody ProductOccasion productOccasion) {
        return productOccasionService.addOccasionToProduct(productOccasion);
    }

    @DeleteMapping
    public void removeOccasionFromProduct(@RequestParam Integer productId, @RequestParam Integer occasionId) {
        productOccasionService.removeOccasionFromProduct(productId, occasionId);
    }
}
