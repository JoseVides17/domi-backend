package com.vides.domi_backend.service;

import com.vides.domi_backend.model.*;
import com.vides.domi_backend.repository.OrderRepository;
import com.vides.domi_backend.repository.ProductRepository;
import com.vides.domi_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order createOrder(User user, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setFecha(LocalDateTime.now());
        order.setEstado(Order.Estado.EN_CAMINO);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para " + product.getNombre());
            }

            product.setStock(product.getStock() - item.getCantidad());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setCantidad(item.getCantidad());
            orderItem.setPrecioUnitario(item.getPrecioUnitario());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        return orderRepository.save(order);
    }


    public Order updateStatus(Long orderId, Order.Estado estado) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setEstado(estado);
        return orderRepository.save(order);
    }

    public List<Order> findByEstado(Order.Estado estado) {
        return orderRepository.findByEstado(estado);
    }

    public List<Order> findByUserAndEstado(User user, Order.Estado estado) {
        return orderRepository.findByUserAndEstado(user, estado);
    }
}
