package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.OrderProductDTO;
import com.example.backendhoatuoiuit.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/order-products")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    @GetMapping("/order/{orderId}")
    public List<OrderProductDTO> getProductsByOrderId(@PathVariable Integer orderId) {
        return orderProductService.getProductsByOrderId(orderId);
    }

    @PostMapping
    public OrderProductDTO addOrderProduct(@RequestBody OrderProductDTO dto) {
        return orderProductService.addOrderProduct(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderProduct(@PathVariable Integer id) {
        orderProductService.deleteOrderProduct(id);
    }

    @GetMapping("/order/{orderId}/total-amount")
    public BigDecimal calculateTotalAmount(@PathVariable Integer orderId) {
        return orderProductService.calculateTotalAmountByOrderId(orderId);
    }

}
