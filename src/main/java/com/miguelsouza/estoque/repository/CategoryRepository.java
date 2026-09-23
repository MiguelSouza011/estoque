package com.miguelsouza.estoque.repository;

import com.miguelsouza.estoque.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
