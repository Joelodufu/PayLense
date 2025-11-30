package com.paylense.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "PayLense API",
        version = "v1",
        description = "API documentation for PayLense REST endpoints",
        contact = @Contact(
            name = "PayLense Support",
            email = "support@paylense.com",
            url = "https://paylense.com/contact"
        )
    ),
    security = @SecurityRequirement(name = "bearerAuth"),
    servers = @Server(url = "/")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {
    // This class is used for OpenAPI Swagger configuration
}
