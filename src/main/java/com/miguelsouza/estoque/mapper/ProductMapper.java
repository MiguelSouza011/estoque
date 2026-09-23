package com.miguelsouza.estoque.mapper;

import com.miguelsouza.estoque.dto.CategoryDTO;
import com.miguelsouza.estoque.dto.ProductDTO;
import com.miguelsouza.estoque.dto.SupplierDTO;
import com.miguelsouza.estoque.entities.Category;
import com.miguelsouza.estoque.entities.Product;
import com.miguelsouza.estoque.entities.Supplier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = SupplierMapper.class)
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    default CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

    default List<SupplierDTO> toSupplierDTOList(List<Supplier> suppliers) {
        if (suppliers == null) return null;
        return suppliers.stream()
                .map(s -> new SupplierDTO(s.getId(), s.getName(), s.getCnpj(), s.getEmail(), s.getPhone()))
                .toList();
    }
}
