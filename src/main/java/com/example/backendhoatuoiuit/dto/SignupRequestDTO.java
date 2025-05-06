package com.example.backendhoatuoiuit.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String role;
}
