package com.miguelsouza.estoque.service;

import com.miguelsouza.estoque.entities.Movement;
import com.miguelsouza.estoque.entities.Product;
import com.miguelsouza.estoque.entities.enuns.TypeMovement;
import com.miguelsouza.estoque.exceptions.BusinessException;
import com.miguelsouza.estoque.exceptions.ResourceNotFoundException;
import com.miguelsouza.estoque.repository.MovementRepository;
import com.miguelsouza.estoque.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MovementService {

    private final MovementRepository repository;
    private final ProductRepository productRepository;

    public List<Movement> findAll() {
        return repository.findAll();
    }

    public Movement findById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }

    public Movement insert(Movement obj) {

        Product product = productRepository.findById(
                obj.getProduct().getId()
        ).orElseThrow(
                () -> new ResourceNotFoundException(
                        obj.getProduct().getId()
                )
        );

        if (obj.getType() == TypeMovement.ENTRADA) {

            product.setQuantity(
                    product.getQuantity() + obj.getQuantity()
            );
        }

        else if (obj.getType() == TypeMovement.SAIDA) {

            if (product.getQuantity() < obj.getQuantity()) {
                throw new BusinessException(
                        "Estoque insuficiente para realizar a saída."
                );
            }

            product.setQuantity(
                    product.getQuantity() - obj.getQuantity()
            );
        }

        productRepository.save(product);

        obj.setProduct(product);
        obj.setDateTime(LocalDateTime.now());

        return repository.save(obj);
    }
}
