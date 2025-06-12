package com.lucas.kopringjpademo.configs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * SwaggerConfig.kt: Swagger MVC UI Config
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 12. 오후 10:41
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
            .title("kopring-jpa-demo API's")
            .description("kopring - JPA & QueryDSL Demo Application")
            .version("1.0.0")
    }

}