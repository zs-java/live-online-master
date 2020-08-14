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

import java.util.ArrayList;
import java.util.List;


/**
 * 类 名: DynamicRouteService
 * 描 述: 动态路由 基础基础方法类
 * 作 者: 朱帅
 * 创 建：2020年08月14日
 */
@Service
@Slf4j
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    /**
     * 动态路由集合
     */
    private List<RouteDefinition> routeDefinitionList = new ArrayList<>();

    /**
     * 描 述： 更新路由
     * 作 者： 朱帅
     * @param definition 路由对象
     * @return boolean
     */
    public synchronized boolean save(RouteDefinition definition) {
        try {
            // 先根据Id删除已经存在的路由
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
            // 注册路由
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            // 往路由集合中添加路由
            this.routeDefinitionList.add(definition);
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
     * 描 述： 根据路由Id删除已经注册的路由
     * 作 者： 朱帅
     * @param id 路由Id
     * @return boolean
     */
    public synchronized boolean delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            // 根据Id删除动态路由集合中的路由
            this.deleteFromRouteDefinitionList(id);
            log.info("删除路由成功，routeId: {}", id);
            return true;
        } catch (Exception e) {
            log.error("删除路由失败，routeId: {}", id);
            return false;
        }
    }

    /**
     * 描 述： 根据路由Id删除集合中的路由
     * 作 者： 张屹峰
     */
    public void deleteFromRouteDefinitionList(String id) {
        try {
            this.routeDefinitionList.removeIf(route -> id.equals(route.getId()));
            log.info("删除路由成功，routeId: {}", id);
        } catch (Exception e) {
            log.error("删除路由失败，routeId: {}", id);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public List<RouteDefinition> getRouteDefinitionList() {
        return routeDefinitionList;
    }
}
