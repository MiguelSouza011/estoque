package com.miguelsouza.estoque.service;

import com.miguelsouza.estoque.entities.Category;
import com.miguelsouza.estoque.entities.Product;
import com.miguelsouza.estoque.exceptions.ResourceNotFoundException;
import com.miguelsouza.estoque.repository.CategoryRepository;
import com.miguelsouza.estoque.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public Product insert(Product obj) {

        Category category = categoryRepository
                .findById(obj.getCategory().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                obj.getCategory().getId()
                        )
                );

        obj.setCategory(category);

        return repository.save(obj);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }



    public Product update(Integer id, Product obj) {
        Product entity = findById(id);
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setQuantity(obj.getQuantity());

        Category category = categoryRepository
                .findById(obj.getCategory().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                obj.getCategory().getId()
                        )
                );

        entity.setCategory(category);

        return repository.save(entity);
    }

    public Product findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(id)
                );
    }

    public void deleteById(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    public List<Product> findByExample(Integer id, String name) {

        Product product = new Product();

        product.setId(id);
        product.setName(name);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example<Product> example = Example.of(product, matcher);

        return repository.findAll(example);
    }
}
