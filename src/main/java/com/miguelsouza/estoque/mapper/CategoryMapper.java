package com.miguelsouza.estoque.mapper;

import com.miguelsouza.estoque.dto.CategoryDTO;
import com.miguelsouza.estoque.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);
}
