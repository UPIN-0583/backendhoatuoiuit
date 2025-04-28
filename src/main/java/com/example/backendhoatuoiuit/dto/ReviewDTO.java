package com.example.backendhoatuoiuit.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Integer id;
    private Integer customerId;
    private Integer productId;
    private Integer rating;
    private String comment;
    private Boolean isVerified;
}
