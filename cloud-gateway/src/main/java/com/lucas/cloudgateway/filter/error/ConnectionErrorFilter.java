package com.lucas.cloudgateway.filter.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucas.cloudgateway.utils.JsonUtil;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
                        errorResponse.put("message", "서비스가 응답하지 않습니다. 잠시 후 다시 시도해주세요.");
                        errorResponse.put("timestamp", LocalDateTime.now().toString());

                        try {
                            String json = JsonUtil.toJson(errorResponse);
                            DataBuffer buffer = response.bufferFactory().wrap(toByte(json));

                            return response.writeWith(Mono.just(buffer));
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage(), e);

                            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                            DataBuffer buffer = response.bufferFactory().wrap(toByte("{\"message\": \"Service Error Please Retry.\"}"));

                            return response.writeWith(Mono.just(buffer));
                        }
                    }

                    // 서버가 응답을 보낸 경우, 원래 응답을 그대로 반환
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -2;
    }

    private byte[] toByte(String value){
        return value.getBytes(StandardCharsets.UTF_8);
    }
}
