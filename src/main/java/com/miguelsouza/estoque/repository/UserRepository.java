package com.miguelsouza.estoque.repository;

import com.miguelsouza.estoque.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByLogin(String login);
}
