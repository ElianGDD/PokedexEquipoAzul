package com.RisosuIT.Pokedex.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${base.url}")
    private String URL_BASE;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16*1024*1024)
                ).build();
        
        return builder
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(URL_BASE)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
