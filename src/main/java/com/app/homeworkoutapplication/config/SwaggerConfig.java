package com.app.homeworkoutapplication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String localPort;

    @Bean
    public OpenAPI customOpenAPI() {
        if(localPort == null) {
            localPort = String.valueOf(8080);
        }
        String devUrl = "http://localhost:" + localPort;
        Server devServer = new Server()
                .url(devUrl) // URL server dev
                .description("Development Server");

        Server prodServer = new Server()
                .url("https://homeworkout.up.railway.app/") // URL server production
                .description("Production Server");

        return new OpenAPI()
                .servers(List.of(devServer, prodServer));
    }
}

