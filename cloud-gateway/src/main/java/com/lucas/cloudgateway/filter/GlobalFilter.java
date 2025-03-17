package com.lucas.cloudgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Mono;

/**
 * @package : com.lucas.cloudgateway.filter
 * @name : GlobalFilter.java
 * @date : 2025. 3. 17. 오후 4:42
 * @author : lucaskang(swings134man)
 * @Description: SPG GlobalFilter
 * - yml: default-filters 추가 가능
 * - SCG 의 Custom Filter 등록 방식
**/
@Component
@Order(-1)
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    private StopWatch stopWatch;

    public GlobalFilter() {
        super(Config.class);
        stopWatch = new StopWatch("API Gateway");
    }

    public static class Config {}

    @Override
    public org.springframework.cloud.gateway.filter.GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // PRE
            stopWatch.start();
            log.info("[GlobalFilter] Request ID: {}", request.getId());
            // TODO: PRE Request Processing

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // POST
                stopWatch.stop();
                log.info("[GlobalFilter] API Execution Time : " + stopWatch.getTotalTimeMillis() + "ms");
            }));
        };
    }


}
