package com.eventify.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8443}")
    private String serverPort;

    @Bean
    public OpenAPI eventifyOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:" + serverPort);
        localServer.setDescription("Local Development Server");


        Info info = new Info()
                .title("Eventify - Secure Event Management System API")
                .version("2.0.0")
                .description("REST API for managing events and participants with JWT authentication. " +
                        "This API allows authenticated users to create events, manage participants, " +
                        "and track event attendance. Each user can only access their own events.");


        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Enter JWT Bearer token. You can obtain the token by registering/logging in.");

        Components components = new Components()
                .addSecuritySchemes("Bearer Authentication", securityScheme);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer))
                .components(components);
    }
}
