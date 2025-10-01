package com.vides.domi_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileDto {

    private String nombre;
    private String email;
    private String phone;
    private String address;
    private String city;
    private LocalDate fechaNacimiento;
    private String profileUrl;

}
