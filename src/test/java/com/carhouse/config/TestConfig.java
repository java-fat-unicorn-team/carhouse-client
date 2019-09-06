package com.carhouse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Configuration
@ComponentScan("com.carhouse.provider")
@PropertySources({@PropertySource("classpath:endpoints.properties"),
@PropertySource("classpath:test.properties")})
public class TestConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public void initializeWireMockServer() {
        WireMockServer wireMockServer = new WireMockServer(options()
                .bindAddress("localhost").port(8090));
        wireMockServer.start();
        configureFor("localhost", 8090);
    }
}
