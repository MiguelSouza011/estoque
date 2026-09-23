package com.miguelsouza.estoque.repository;

import com.miguelsouza.estoque.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Integer> {
}
