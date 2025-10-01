package com.vides.domi_backend.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String nombre;
    private String email;
    private String password;
}
