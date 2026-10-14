package com.miguelsouza.estoque.controller;

import com.miguelsouza.estoque.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;
    private final String destinatario;

    public EmailController(
            EmailService emailService,
            @Value("${stock.alert.recipient}") String destinatario) {
        this.emailService = emailService;
        this.destinatario = destinatario;
    }

    @PostMapping
    public ResponseEntity<String> testar() {
        emailService.enviar(
                destinatario,
                "Teste do sistema de estoque",
                "Se você recebeu este e-mail, a integração SMTP está funcionando!"
        );

        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }
}

