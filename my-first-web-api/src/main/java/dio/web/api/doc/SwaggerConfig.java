package dio.web.api.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()   
                .contact(new Contact()
                .name("Seu nome")
                .url("http://www.meusite.com.br")
                .email("Rebolation@gmail.com")));
    }
}
