package com.miguelsouza.estoque.entities;

import com.miguelsouza.estoque.entities.enuns.TypeMovement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao")
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "Tipo é obrigatório")
    private TypeMovement type;
    @Column(name = "quantidade")
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantity;
    @Column(name = "data")
    private LocalDateTime dateTime;
    @ManyToOne
    @NotNull(message = "Produto é obrigatório")
    @JoinColumn(name = "id_produto", nullable = false)
    private Product product;
}
