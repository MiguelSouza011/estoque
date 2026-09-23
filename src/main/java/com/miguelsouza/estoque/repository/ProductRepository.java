package com.miguelsouza.estoque.repository;

import com.miguelsouza.estoque.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByName(String name);

    List<Product> findByNameAndId(String name, Integer id);
}
