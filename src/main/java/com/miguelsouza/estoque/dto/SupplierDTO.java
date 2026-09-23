package com.miguelsouza.estoque.dto;

public record SupplierDTO(
        Integer id,
        String name,
        String cnpj,
        String email,
        String phone
) {}

