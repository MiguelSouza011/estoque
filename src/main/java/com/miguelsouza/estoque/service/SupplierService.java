package com.miguelsouza.estoque.service;

import com.miguelsouza.estoque.entities.Supplier;
import com.miguelsouza.estoque.exceptions.BusinessException;
import com.miguelsouza.estoque.exceptions.ResourceNotFoundException;
import com.miguelsouza.estoque.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;

    public Supplier insert(Supplier obj) {

        if (repository.existsByCnpj(obj.getCnpj())) {
            throw new BusinessException(
                    "Já existe um fornecedor com esse CNPJ."
            );
        }

        return repository.save(obj);
    }

    public List<Supplier> get(String name, String cnpj) {

        if (!StringUtils.hasText(name) && !StringUtils.hasText(cnpj)) {
            return repository.findAll();
        }

        Supplier supplier = new Supplier();
        supplier.setName(StringUtils.hasText(name) ? name : null);
        supplier.setCnpj(StringUtils.hasText(cnpj) ? cnpj : null);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnorePaths("products");

        Example<Supplier> example = Example.of(supplier, matcher);

        return repository.findAll(example);
    }

    public Supplier findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(id)
                );
    }

    public Supplier update(Integer id, Supplier obj) {

        Supplier entity = findById(id);

        if (repository.existsByCnpjAndIdNot(obj.getCnpj(), id)) { throw new BusinessException( "Já existe outro fornecedor com esse CNPJ." ); }

        entity.setName(obj.getName());
        entity.setCnpj(obj.getCnpj());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());

        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
