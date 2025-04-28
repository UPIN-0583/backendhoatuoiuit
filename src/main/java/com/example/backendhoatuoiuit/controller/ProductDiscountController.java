package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.entity.ProductDiscount;
import com.example.backendhoatuoiuit.service.ProductDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-discount")
public class ProductDiscountController {

    @Autowired
    private ProductDiscountService productDiscountService;

    @GetMapping("/product/{productId}")
    public List<ProductDiscount> getDiscountsByProduct(@PathVariable Integer productId) {
        return productDiscountService.getDiscountsByProductId(productId);
    }

    @PostMapping
    public ProductDiscount addDiscountToProduct(@RequestBody ProductDiscount productDiscount) {
        return productDiscountService.addDiscountToProduct(productDiscount);
    }

    @DeleteMapping
    public void removeDiscountFromProduct(@RequestParam Integer productId, @RequestParam Integer discountId) {
        productDiscountService.removeDiscountFromProduct(productId, discountId);
    }
}
