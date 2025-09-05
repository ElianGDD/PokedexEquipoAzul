package com.RisosuIT.Pokedex.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${base.url}")
    private String URL_BASE;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(URL_BASE)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
