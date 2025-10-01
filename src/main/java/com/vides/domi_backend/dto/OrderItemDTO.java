package com.vides.domi_backend.dto;

import com.vides.domi_backend.model.OrderItem;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productoId;
    private String nombre;
    private Integer cantidad;
    private Double precioUnitario;

    public static OrderItemDTO fromEntity(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductoId(item.getProduct().getId());
        dto.setNombre(item.getProduct().getNombre());
        dto.setCantidad(item.getCantidad());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        return dto;
    }
}
