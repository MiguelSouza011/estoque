package com.miguelsouza.estoque.controller;

import com.miguelsouza.estoque.entities.Category;
import com.miguelsouza.estoque.entities.Movement;
import com.miguelsouza.estoque.service.MovementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
@AllArgsConstructor
public class MovementController {

    private final MovementService service;

    @PostMapping
    public ResponseEntity<Movement> insert(@Valid @RequestBody Movement obj) {

        obj = service.insert(obj);

        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> findById(@PathVariable Integer id) {

        Movement obj = service.findById(id);

        return ResponseEntity.ok(obj);
    }

    @GetMapping
    public ResponseEntity<List<Movement>> findAll() {
        List<Movement> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}
