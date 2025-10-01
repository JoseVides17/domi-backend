package com.vides.domi_backend.controller;

import com.vides.domi_backend.model.Product;
import com.vides.domi_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<Product> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size);

        if (search != null && !search.isEmpty()) {
            return productService.findByNombre(search, pageable);
        } else {
            return productService.findAll(pageable);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {

        if (product.getImagenUrl() != null) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(product.getImagenUrl());

                String fileName = UUID.randomUUID().toString() + ".png";

                Path uploadPath = Paths.get("uploads");
                Path filePath = uploadPath.resolve(fileName);

                Files.write(filePath, imageBytes);
                product.setImagenUrl(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return productService.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return productService.findById(id)
                .map(p -> {
                    p.setNombre(product.getNombre());
                    p.setPrecio(product.getPrecio());
                    p.setStock(product.getStock());
                    p.setImagenUrl(product.getImagenUrl());
                    return ResponseEntity.ok(productService.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
