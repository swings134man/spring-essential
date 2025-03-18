package com.lucas.cloudgateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @package : com.lucas.cloudgateway.route
 * @name : SampleRouteConfig.java
 * @date : 2025. 3. 18. 오후 4:32
 * @author : lucaskang(swings134man)
 * @Description: RouteLocator 설정 Sample Class
**/
@Configuration
public class SampleRouteConfig {

    @Bean
    public RouteLocator locator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("server-one", r -> r
                        .path("/api/one/**")
                        .uri("http://localhost:8081"))

                .route("server-two", r -> r
                        .path("/api/two/**")
                        .uri("http://localhost:8082")
                )
                .build();
    }
}
