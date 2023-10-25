package com.lauro.correia.reactive.api.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfiguration {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("spring-reactive-api")
            .description("An reactive API using Spring WebFlux")
            .contact(new Contact()
                .name("Lauro Correia")
                .email("lauro.silveira@outlook.com.br"))
            .license(new License()
                .name("Apache 2.0")
                .url("http://voll.med/api/licenca")
            )
        );
  }

}
