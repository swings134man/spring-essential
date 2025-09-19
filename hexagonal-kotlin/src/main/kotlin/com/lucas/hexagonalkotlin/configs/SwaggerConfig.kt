package com.lucas.hexagonalkotlin.configs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * SwaggerConfig.kt: Swagger Configuration
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 4:25
 * @description: http://localhost:8080/swagger-ui/index.html
 */
@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(configurationInfo())
    }

    private fun configurationInfo(): Info {
        return Info()
            .title("hexagonal-kotlin API's")
            .description("hexagonal - Demo Application")
            .version("1.0.0")
    }

}