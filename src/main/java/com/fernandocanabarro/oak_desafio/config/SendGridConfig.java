package com.fernandocanabarro.oak_desafio.config;

import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SendGridConfig {

    // @Bean
    // public SendGrid sendGrid() {
    //     // Use a chave de API configurada como variável de ambiente
    //     String apiKey = System.getenv("SENDGRID_API_KEY");
    //     if (apiKey == null || apiKey.isBlank()) {
    //         throw new IllegalStateException("SENDGRID_API_KEY não configurado");
    //     }
    //     return new SendGrid(apiKey);
    // }
}
