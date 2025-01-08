package ru.avanes3303.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info()
                        .title("Banking System API")
                        .version("1.0")
                )
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
