package com.example.volunteerapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "VolunteerApp API", version = "v1"),
        servers = @Server(url = "/", description = "Default Server URL"),
        security = @SecurityRequirement(name = "bearerAuth")        // apply globally
)
@SecurityScheme(
        name = "bearerAuth",                                        // must match @SecurityRequirement name
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = "Enter your JWT token as: Bearer <token>"
)
public class SpringDocConfig {
    // no methods needed; the annotations do all the work
}
