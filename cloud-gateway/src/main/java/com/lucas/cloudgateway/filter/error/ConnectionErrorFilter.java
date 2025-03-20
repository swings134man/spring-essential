package com.lucas.cloudgateway.filter.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @package : com.lucas.cloudgateway.filter.error
 * @name : ConnectionErorrFilter.java
 * @date : 2025. 3. 20. 오후 3:47
 * @author : lucaskang(swings134man)
 * @Description: 각 Server 와의 Connection Error 발생시 처리 Filter
**/
@Component
@Slf4j
public class ConnectionErrorFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(throwable -> {
                    log.error("Connection Error : {}", throwable.getMessage());

                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                    // 서버가 응답하지 않는 경우 - Shutdown, Timeout
                    if (throwable instanceof ConnectException || throwable instanceof TimeoutException) {
                        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                        String errorMessage = "{\"message\": \"서비스가 응답하지 않습니다. 잠시 후 다시 시도해주세요.\"}";
                        DataBuffer buffer = response.bufferFactory().wrap(errorMessage.getBytes(StandardCharsets.UTF_8));
                        return response.writeWith(Mono.just(buffer));
                    }

                    // 서버가 응답을 보낸 경우, 원래 응답을 그대로 반환
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
