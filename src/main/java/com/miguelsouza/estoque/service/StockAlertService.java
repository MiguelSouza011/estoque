package com.miguelsouza.estoque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockAlertService {

    private final ProductService productService;
    private final EmailService emailService;

    @Value("${stock.alert.recipient}")
    private String destinatario;

    @Scheduled(fixedRate = 300000, initialDelay = 10000)
    public void checkLowStock() {

        var products = productService.findLowStock();

        if (products.isEmpty()) {
            return;
        }

        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Os seguintes produtos precisam de reposição:\n\n");

        products.forEach(product ->
                mensagem.append("- ")
                        .append(product.getName())
                        .append(" | Quantidade: ")
                        .append(product.getQuantity())
                        .append("\n")
        );

        emailService.enviar(
                destinatario,
                "Alerta de estoque baixo",
                mensagem.toString()
        );
    }
}

