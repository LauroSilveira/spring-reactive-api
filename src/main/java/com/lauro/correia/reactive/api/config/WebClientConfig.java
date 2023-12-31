package com.lauro.correia.reactive.api.config;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class WebClientConfig {

    @Bean
    public WebClient createWebClient() {
        return  WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }
}
