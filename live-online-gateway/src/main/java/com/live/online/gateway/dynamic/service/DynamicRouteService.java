package com.live.online.gateway.dynamic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 动态路由 基础基础方法类
 * @author 朱帅
 */
@Service
@Slf4j
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    /**
     * 更新路由
     * @param definition route
     * @return result
     */
    public synchronized boolean save(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            log.info("更新路由成功，routeId: {}", definition.getId());
            return true;
        } catch (Exception e) {
            log.error("更新路由失败，routeId: {}", definition.getId());
            return false;
        }
    }

    public void save(List<RouteDefinition> routeDefinitionList) {
        routeDefinitionList.forEach(this::save);
    }

    /**
     * 删除路由
     * @param id route id
     * @return result
     */
    public boolean delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            log.info("删除路由成功，routeId: {}", id);
            return true;
        } catch (Exception e) {
            log.error("删除路由失败，routeId: {}", id);
            return false;
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
