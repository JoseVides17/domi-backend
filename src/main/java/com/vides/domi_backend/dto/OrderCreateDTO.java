package com.vides.domi_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderCreateDTO {
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Long productId;
        private Integer cantidad;
        private Double precioUnitario;
    }
}
