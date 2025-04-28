package com.example.backendhoatuoiuit.mapper;

import com.example.backendhoatuoiuit.dto.OrderDTO;
import com.example.backendhoatuoiuit.entity.Customer;
import com.example.backendhoatuoiuit.entity.Order;
import com.example.backendhoatuoiuit.entity.OrderStatus;
import com.example.backendhoatuoiuit.entity.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer() != null ? order.getCustomer().getId() : null);
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setPaymentId(order.getPayment() != null ? order.getPayment().getId() : null);
        dto.setNote(order.getNote());
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        if (dto.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setId(dto.getCustomerId());
            order.setCustomer(customer);
        }
        order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : LocalDateTime.now());
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(OrderStatus.valueOf(dto.getStatus()));
        if (dto.getPaymentId() != null) {
            Payment payment = new Payment();
            payment.setId(dto.getPaymentId());
            order.setPayment(payment);
        }
        order.setNote(dto.getNote());
        return order;
    }
}
