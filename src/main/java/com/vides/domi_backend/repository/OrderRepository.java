package com.vides.domi_backend.repository;

import com.vides.domi_backend.model.Order;
import com.vides.domi_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findByEstado(Order.Estado estado);

    List<Order> findByUserAndEstado(User user, Order.Estado estado);
}
