package com.example.backendhoatuoiuit.dto;

import lombok.Data;

@Data
public class OccasionDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private String slug;
    private String imageUrl;
}
