package com.miguelsouza.estoque.mapper;

import com.miguelsouza.estoque.dto.SupplierDTO;
import com.miguelsouza.estoque.entities.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "cnpj", target = "cnpj")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    Supplier toEntity(SupplierDTO dto);

    SupplierDTO toDTO(Supplier supplier);
}
