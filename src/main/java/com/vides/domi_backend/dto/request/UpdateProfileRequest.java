package com.vides.domi_backend.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequest {
    private String nombre;
    private String phone;
    private String address;
    private String city;
    private LocalDate fechaNacimiento;
    private String profileUrl;
}
