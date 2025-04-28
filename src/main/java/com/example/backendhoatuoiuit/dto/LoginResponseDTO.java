package com.example.backendhoatuoiuit.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private Boolean isActive;
}
