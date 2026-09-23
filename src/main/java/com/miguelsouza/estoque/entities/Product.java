package com.miguelsouza.estoque.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome", nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    @Column(name = "descricao")
    @Size(max = 70, message = "Descrição deve ter no máximo 70 caracteres")
    private String description;
    @Column(name = "preco")
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private Double price;
    @Column(name = "quantidade")
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantity;
    @ManyToOne
    @NotNull(message = "Categoria é obrigatória")
    @JoinColumn(name = "id_categoria")
    private Category category;
    @ManyToMany
    @JoinTable(
            name = "produto_fornecedor",
            joinColumns = @JoinColumn(name = "id_produto"),
            inverseJoinColumns = @JoinColumn(name = "id_fornecedor"))
    private List<Supplier> suppliers = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Movement> movements = new ArrayList<>();
}
