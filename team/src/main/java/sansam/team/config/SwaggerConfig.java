package sansam.team.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    

    private Info apiInfo() {
        return new Info()
                .title("SanSam API TEST") // API의 제목
                .description("API Swagger 테스트") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}