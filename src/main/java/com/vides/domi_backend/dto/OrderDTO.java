package com.vides.domi_backend.dto;

import com.vides.domi_backend.model.Order;
import lombok.Data;
import org.hibernate.sql.results.spi.LoadContexts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {
    private Long id;
    private LocalDateTime fecha;
    private String estado;
    private List<OrderItemDTO> items;

    public static OrderDTO fromEntity(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setEstado(order.getEstado().toString());
        dto.setFecha(order.getFecha());
        dto.setItems(order.getItems().stream()
                .map(OrderItemDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }
}
