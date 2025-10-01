package com.vides.domi_backend.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String imagenUrl;
}
