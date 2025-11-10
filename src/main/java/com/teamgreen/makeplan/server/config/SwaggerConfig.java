package com.teamgreen.makeplan.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "bearer"; // ⚠️ 소문자여야 Swagger 인식됨

    @Bean
    public OpenAPI openAPI() {
        String securityJwtName = "JWT";

        // JWT 인증 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        Components components = new Components()
                .addSecuritySchemes(securityJwtName, new SecurityScheme()
                        .name(securityJwtName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(new Info()
                              .title("Makeplan API 문서")
                              .description("JWT 인증이 적용된 Swagger UI")
                              .version("1.0.0"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}