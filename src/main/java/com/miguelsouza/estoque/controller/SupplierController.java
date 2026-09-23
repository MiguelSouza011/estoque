package com.miguelsouza.estoque.controller;

import com.miguelsouza.estoque.dto.SupplierDTO;
import com.miguelsouza.estoque.entities.Supplier;
import com.miguelsouza.estoque.mapper.SupplierMapper;
import com.miguelsouza.estoque.service.SupplierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService service;
    private final SupplierMapper mapper;

    @PostMapping
    public ResponseEntity<SupplierDTO> insert(@Valid @RequestBody Supplier obj) {
        obj = service.insert(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(obj));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> findById(@PathVariable Integer id) {
        Supplier obj = service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> find(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cnpj", required = false) String cnpj) {
        List<Supplier> result = service.get(name, cnpj);
        List<SupplierDTO> dto = result.stream().map(mapper::toDTO).toList();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody Supplier obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok(mapper.toDTO(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
