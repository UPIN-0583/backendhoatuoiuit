package com.example.backendhoatuoiuit.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Integer id;
    private Integer customerId;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private String status;
    private Integer paymentId;
    private String note;
}
