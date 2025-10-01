package com.vides.domi_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String phone;
    private String address;
    private String city;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    private String profileUrl;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(nullable = true)
    private boolean enabled = false;

    public enum Rol {
        USER, ADMIN
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDate.now();
    }
}
