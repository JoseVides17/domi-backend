package com.vides.domi_backend.controller;

import com.vides.domi_backend.dto.OrderCreateDTO;
import com.vides.domi_backend.dto.OrderDTO;
import com.vides.domi_backend.model.*;
import com.vides.domi_backend.repository.ProductRepository;
import com.vides.domi_backend.repository.UserRepository;
import com.vides.domi_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @GetMapping
    public List<OrderDTO> getOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Order.Estado estado) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        if (user.getRol() == User.Rol.ADMIN) {
            return estado != null
                    ? orderService.findByEstado(estado).stream().map(OrderDTO::fromEntity).toList()
                    : orderService.findAll().stream().map(OrderDTO::fromEntity).toList();
        } else {
            return estado != null
                    ? orderService.findByUserAndEstado(user, estado).stream().map(OrderDTO::fromEntity).toList()
                    : orderService.findByUser(user).stream().map(OrderDTO::fromEntity).toList();
        }
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderCreateDTO dto) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        List<OrderItem> items = dto.getItems().stream()
                .map(i -> {
                    Product product = productRepository.findById(i.getProductId())
                            .orElseThrow();

                    return OrderItem.builder()
                            .product(product)
                            .cantidad(i.getCantidad())
                            .precioUnitario(i.getPrecioUnitario())
                            .build();
                })
                .toList();

        Order order = orderService.createOrder(user, items);

        return ResponseEntity.status(HttpStatus.CREATED).body(OrderDTO.fromEntity(order));
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestParam Order.Estado estado) {
        Order order = orderService.updateStatus(id, estado);
        return ResponseEntity.ok(OrderDTO.fromEntity(order));
    }
}
