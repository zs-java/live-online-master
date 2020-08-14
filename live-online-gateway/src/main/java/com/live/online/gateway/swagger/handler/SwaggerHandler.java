package com.live.online.gateway.swagger.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * 类 名: SwaggerHandler
 * 描 述: Swagger Handler
 * 作 者: 张屹峰
 * 创 建：2020年08月14日
 * <p>
 * 历 史: (版本) 作者 时间 注释
 */
@RestController
public class SwaggerHandler  {

    private final SecurityConfiguration securityConfiguration;

    private final UiConfiguration uiConfiguration;

    private final SwaggerResourcesProvider swaggerResources;

    @Autowired
    public SwaggerHandler(SwaggerResourcesProvider swaggerResources, SecurityConfiguration securityConfiguration, UiConfiguration uiConfiguration) {
        this.swaggerResources = swaggerResources;
        this.securityConfiguration = securityConfiguration;
        this.uiConfiguration = uiConfiguration;
    }

    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity<?>> swaggerResources() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }

}
