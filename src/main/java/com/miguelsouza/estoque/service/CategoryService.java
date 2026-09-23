package com.miguelsouza.estoque.service;

import com.miguelsouza.estoque.entities.Category;
import com.miguelsouza.estoque.exceptions.ResourceNotFoundException;
import com.miguelsouza.estoque.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }

    public Category insert(Category category) {
        return repository.save(category);
    }

    public Category update(Integer id, Category obj) {
        Category entity = findById(id);
        entity.setName(obj.getName());
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
