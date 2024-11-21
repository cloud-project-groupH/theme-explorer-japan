package com.CPGroupH.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Profile({"local", "dev"})
@Configuration
public class SwaggerConfig {
    private Info info(){
        return new Info().title("IsshoniGo API Swagger")
                .version("1.0")
                .description("IsshoniGo API를 위한 swagger 입니다.");
    }

    @Bean
    public OpenAPI openAPI(){
        String jwtScheme = "jwtAuth";
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("IsshoniGo API");

        Components components = new Components()
                .addSecuritySchemes(jwtScheme, new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI().info(info())
                .components(components)
                .servers(List.of(server));
    }
}
