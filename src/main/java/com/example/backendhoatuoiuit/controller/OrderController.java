package com.example.backendhoatuoiuit.controller;

import com.example.backendhoatuoiuit.dto.OrderDTO;
import com.example.backendhoatuoiuit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/revenue/date")
    public BigDecimal getRevenueByDate(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        return orderService.getTotalRevenueByDate(date);
    }

    @GetMapping("/revenue/month")
    public BigDecimal getRevenueByMonth(@RequestParam("month") int month, @RequestParam("year") int year) {
        return orderService.getTotalRevenueByMonth(month, year);
    }

    @PutMapping("/{orderId}/status")
    public OrderDTO updateOrderStatus(@PathVariable Integer orderId, @RequestParam("status") String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @PostMapping("/create-from-cart/{customerId}")
    public OrderDTO createOrderFromCart(@PathVariable Integer customerId) {
        return orderService.createOrderFromCart(customerId);
    }

    @PutMapping("/{orderId}/delivery-address")
    public OrderDTO updateDeliveryAddress(@PathVariable Integer orderId, @RequestParam("address") String address) {
        return orderService.updateDeliveryAddress(orderId, address);
    }

    @PutMapping("/{orderId}/payment-method")
    public OrderDTO updatePaymentMethod(@PathVariable Integer orderId, @RequestParam("paymentId") Integer paymentId) {
        return orderService.updatePaymentMethod(orderId, paymentId);
    }

    @PutMapping("/{orderId}/confirm")
    public OrderDTO confirmOrder(@PathVariable Integer orderId) {
        return orderService.confirmOrder(orderId);
    }

    @PutMapping("/{orderId}/cancel")
    public OrderDTO cancelOrder(@PathVariable Integer orderId) {
        return orderService.cancelOrder(orderId);
    }

    @PutMapping("/{orderId}/ship")
    public OrderDTO shipOrder(@PathVariable Integer orderId) {
        return orderService.shipOrder(orderId);
    }

    @PutMapping("/{orderId}/delivered")
    public OrderDTO markAsDelivered(@PathVariable Integer orderId) {
        return orderService.markAsDelivered(orderId);
    }



}
