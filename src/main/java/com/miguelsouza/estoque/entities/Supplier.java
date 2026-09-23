package com.miguelsouza.estoque.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fornecedor")
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome")
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String name;
    @Column(name = "cnpj")
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 caracteres")
    private String cnpj;
    @Column(name = "email")
    @Email(message = "Email inválido")
    private String email;
    @Column(name = "telefone")
    @Pattern(regexp = "\\d{11,14}", message = "Telefone deve ter 11 ou 14 caracteres")
    private String phone;
    @JsonIgnore
    @ManyToMany(mappedBy = "suppliers")
    private List<Product> products = new ArrayList<>();
}
