package com.lucas.cloudgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @package : com.lucas.cloudgateway.filter
 * @name : LoggingFilter.java
 * @date : 2025. 3. 17. 오후 5:30
 * @author : lucaskang(swings134man)
 * @Description: SCG LoggingFilter - Log 처리 Filter
 * - TODO: Kafka || ELK 연동
 * - 전역적으로 모든 Request Logging 처리
**/
@Component
@Order(1)
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.config> {

    public LoggingFilter() {
        super(config.class);
    }

    public static class config {}

    @Override
    public GatewayFilter apply(config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            log.info("[LoggingFilter] 요청: {} {}, Headers: {}",
                    request.getMethod(), request.getURI(), request.getHeaders());

            // TODO: Logging Processing

            return chain.filter(exchange);
        };
    }


}
