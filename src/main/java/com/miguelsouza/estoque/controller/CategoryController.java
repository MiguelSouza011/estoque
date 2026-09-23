package com.miguelsouza.estoque.controller;

import com.miguelsouza.estoque.dto.CategoryDTO;
import com.miguelsouza.estoque.entities.Category;
import com.miguelsouza.estoque.mapper.CategoryMapper;
import com.miguelsouza.estoque.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<Category> list = service.findAll();
        List<CategoryDTO> dto = list.stream().map(mapper::toDTO).toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Integer id) {
        Category obj = service.findById(id);
        return ResponseEntity.ok().body(mapper.toDTO(obj));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody Category category) {
        category = service.insert(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update (@PathVariable Integer id, @Valid @RequestBody Category obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
