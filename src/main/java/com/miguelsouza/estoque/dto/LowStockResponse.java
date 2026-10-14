package com.miguelsouza.estoque.dto;

import java.util.List;

public record LowStockResponse(
        Integer threshold,
        List<ProductDTO> products
) {
}
