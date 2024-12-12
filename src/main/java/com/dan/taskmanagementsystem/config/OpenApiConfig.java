package com.dan.taskmanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Даниил"
                ),
                description = "OpenApi документация для Task Management System от Даниила Астафьева",
                title = "OpenApi спецификация от Даниила Астафьева"
        ),
        servers = {
                @Server(
                        description = "Локальное окружение",
                        url = "http://localhost:8081"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "jwtAuth"
                )
        }
)
@SecurityScheme(
        name = "jwtAuth",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
