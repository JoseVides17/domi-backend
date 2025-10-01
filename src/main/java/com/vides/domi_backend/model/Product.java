package com.vides.domi_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 100)
    private String descripcion;
    private Double precio;
    private Integer stock;

    @Column(nullable = true)
    private String imagenUrl;
}
