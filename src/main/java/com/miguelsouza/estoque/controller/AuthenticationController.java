package com.miguelsouza.estoque.controller;

import com.miguelsouza.estoque.dto.AuthenticationDTO;
import com.miguelsouza.estoque.dto.LoginResponseDTO;
import com.miguelsouza.estoque.dto.RegisterDTO;
import com.miguelsouza.estoque.entities.User;
import com.miguelsouza.estoque.entities.enuns.Role;
import com.miguelsouza.estoque.repository.UserRepository;
import com.miguelsouza.estoque.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto) {
        var UserNamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = authenticationManager.authenticate(UserNamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegisterDTO dto) {

        if (this.repository.findByLogin(dto.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encodedPassword =
                new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User(
                dto.login(),
                encodedPassword,
                Role.USER
        );

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> registerAdmin(
            @RequestBody @Valid RegisterDTO dto) {

        if (this.repository.findByLogin(dto.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encodedPassword =
                new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User(
                dto.login(),
                encodedPassword,
                Role.ADMIN
        );

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
