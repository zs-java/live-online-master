package com.live.online.gateway.swagger.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 类 名: SwaggerHeaderFilter
 * 描 述: Swagger Header 过滤器
 * 作 者: 张屹峰
 * 创 建：2020年08月14日
 * <p>
 * 历 史: (版本) 作者 时间 注释
 */
@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory<Object> {

    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    private static final String SWAGGER_BASE_URI = "/v2/api-docs";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, SWAGGER_BASE_URI)) {
                return chain.filter(exchange);
            }
            String basePath = path.substring(0, path.lastIndexOf(SWAGGER_BASE_URI));
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }

}
