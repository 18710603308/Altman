/*
package com.mzy.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

*/
/**
 * @Author Jack Miao
 * @date 2021/2/24 15:34
 * @desc
 *//*

@Configuration
public class LoginFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> mzy = headers.get("mzy");
        System.out.println(mzy);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
*/
