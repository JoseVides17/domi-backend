package com.vides.domi_backend.repository;

import com.vides.domi_backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
