package pe.edu.vallegrande.monitoreo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .servers(Collections.singletonList(new Server().url("https://didactic-xylophone-pxrgj4jxq54fjrr-8080.app.github.dev/")))
                .info(new Info()
                        .title("MONITOREP API")
                        .description("AS222S5_be PRS1")
                        .version("1.0.0")
                );
    }
}
