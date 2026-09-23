package com.miguelsouza.estoque.dto;

import java.util.List;

public record ProductDTO(
        Integer id,
        String name,
        String description,
        Double price,
        Integer quantity,
        CategoryDTO category,
        List<SupplierDTO> suppliers
){
}
