package com.CPGroupH.swagger;

import com.CPGroupH.domains.auth.security.oauth2.enums.SocialType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local", "dev"})
@Configuration
public class SwaggerConfig {

    private final String SOCIAL_TAG = "\uD83D\uDE80 소셜 로그인";

    @Value("${backend.base-url}")
    private String backendBaseURL;

    private Info info(){
        return new Info().title("IsshoniGo API Swagger")
                .version("1.0")
                .description("IsshoniGo API를 위한 swagger 입니다.");
    }

    @Bean
    public OpenAPI openAPI(OpenApiCustomizer openApiCustomizer){
        String jwtScheme = "Bearer token";
        Server server = new Server();
        server.setUrl(backendBaseURL);
        server.setDescription("IsshoniGo API");

        Components components = new Components()
                .addSecuritySchemes(jwtScheme, new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"));

        OpenAPI openAPI = new OpenAPI().info(info())
                .components(components)
                .servers(List.of(server))
                .addSecurityItem(new SecurityRequirement().addList("Bearer token"))
                .tags(List.of(new Tag().name(SOCIAL_TAG)
                        .description("OAuth2 endpoint")))
                .path("/oauth2/authorization/kakao", pathItem(SocialType.KAKAO));

        openApiCustomizer.customise(openAPI);

        return openAPI;
    }

    private PathItem pathItem(SocialType socialType){
        String socialId = socialType.getRegistrationId();
        String socialTitle = socialType.getTitle();
        return new PathItem().get(new Operation()
                .tags(List.of(SOCIAL_TAG))
                .summary(socialTitle)
                // 인증 비활성화
                .security(List.of())
                .description(String.format("[%s](%s/oauth2/authorization/%s)", socialTitle, backendBaseURL, socialId))
                .responses(new ApiResponses()
                        .addApiResponse("302", new ApiResponse()
                                .content(new Content().addMediaType("application/json",
                                        new MediaType().schema(new Schema<Map<String, String>>()
                                                .type("object")
                                                .example(Map.of(
                                                        "Set-Cookie",
                                                        "accessToken=eyJhiwibmFtZSI6I...; Max-Age=3600; Path=/; Domain=...; HttpOnly=false; Secure=false, refreshToken=dGhpcy1pcy1hLXRlc3QtcmVmcmVzaC10b2tlbg; Max-Age=3600; Path=/; Domain=...; HttpOnly=false; Secure=false"
                                                ))))))));
    }
}
