package com.live.online.gateway.swagger.config;

import com.live.online.gateway.dynamic.service.DynamicRouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;


/**
 * 类 名: SwaggerResourceConfig
 * 描 述: Swagger资源配置类 用来统一管理每个服务的Swagger Api
 * 作 者: 张屹峰
 * 创 建：2020年08月14日
 */
@Component
@Primary
@AllArgsConstructor
@Slf4j
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    /**
     * 动态路由基础Service
     */
    private DynamicRouteService dynamicRouteService;

    /**
     * Swagger Version
     */
    public static final String SWAGGER_VERSION = "2.0";

    /**
     * PredicateDefinition 对象 Args key
     */
    public static final String ARGS_PARAM_NAME = "pattern";

    /**
     * Swagger Base Url
     */
    private static final String SWAGGER_BASE_URI = "/v2/api-docs";

    @Autowired
    public SwaggerResourceConfig(DynamicRouteService dynamicRouteService, RouteLocator routeLocator) {
        this.dynamicRouteService = dynamicRouteService;
        this.routeLocator = routeLocator;
    }

    /**
     * 描 述： 从路由中找出所有服务并获取 Swagger Api 资源
     * 作 者： 张屹峰
     * @return Swagger资源集合
     */
    @Override
    public List<SwaggerResource> get() {
        // 路由 Id 集合
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        // Swagger Resource
        List<SwaggerResource> resources = new ArrayList<>();
        // 从动态获取的路由中获取Args参数并添加到 Swagger Resource 中
        dynamicRouteService.getRouteDefinitionList().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId())).forEach(route -> {
            route.getPredicates().stream()
                    .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                    .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
                            predicateDefinition.getArgs().get(ARGS_PARAM_NAME)
                                    .replace("**", SWAGGER_BASE_URI))));
        });
        return resources;
    }

    /**
     * 描 述： 组装 Swagger 资源对象
     * 作 者： 张屹峰
     * @param name route id
     * @param location router swagger url
     * @return Swagger 资源对象
     */
    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}",name,location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(SWAGGER_VERSION);
        return swaggerResource;
    }

}
