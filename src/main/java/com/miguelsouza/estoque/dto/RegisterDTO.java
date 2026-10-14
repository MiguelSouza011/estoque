package com.miguelsouza.estoque.dto;

import com.miguelsouza.estoque.entities.enuns.Role;

public record RegisterDTO(
        String login,
        String password,
        Role role
) {
}
