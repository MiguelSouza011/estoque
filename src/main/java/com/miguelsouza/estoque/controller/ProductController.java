package com.miguelsouza.estoque.controller;

import com.miguelsouza.estoque.dto.ProductDTO;
import com.miguelsouza.estoque.entities.Product;
import com.miguelsouza.estoque.mapper.ProductMapper;
import com.miguelsouza.estoque.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody Product obj) {
        obj = service.insert(obj);
        ProductDTO dto = mapper.toDTO(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
        Product obj = service.findById(id);
        ProductDTO dto = mapper.toDTO(obj);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody Product obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> find(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "id", required = false) Integer id){
        List<Product> result = service.findByExample(id, name);
        List<ProductDTO> dto = result.stream().map(mapper::toDTO).toList();
        return ResponseEntity.ok(dto);
    }

}
