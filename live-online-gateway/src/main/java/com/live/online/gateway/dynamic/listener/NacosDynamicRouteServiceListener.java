package com.live.online.gateway.dynamic.listener;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.live.online.common.core.utils.JsonUtils;
import com.live.online.gateway.dynamic.service.DynamicRouteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Nacos Config Router Listener
 * @author 朱帅
 */
@Component
@Slf4j
public class NacosDynamicRouteServiceListener implements CommandLineRunner {

    @Value("${spring.profiles.active:}")
    private String profile;
    @Value("${spring.application.name:}")
    private String appName;

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

	@Autowired
    private DynamicRouteService dynamicRouteService;

    @Override
    public void run(String... args) {
        dynamicRouteByNacosListener();
    }

    /**
     * 监听Nacos Server下发的动态路由配置
     */
    public void dynamicRouteByNacosListener (){
        try {
            String dataId = getDataId();
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, nacosConfigProperties.getServerAddr());
            properties.put(PropertyKeyConst.NAMESPACE, nacosConfigProperties.getNamespace());
            ConfigService configService = NacosFactory.createConfigService(properties);
            String content = configService.getConfig(dataId, nacosConfigProperties.getGroup(), nacosConfigProperties.getTimeout());
            // 初始化路由
            initRouters(content);
            configService.addListener(dataId, nacosConfigProperties.getGroup(), new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                	List<RouteDefinition> list = JsonUtils.parseArray(configInfo, RouteDefinition.class);
                    dynamicRouteService.save(list);
                }
                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
           throw new RuntimeException(e);
        }
    }


    private void initRouters(String configInfo) {
        log.info("初始化网关路由开始");
        List<RouteDefinition> routers = JsonUtils.parseArray(configInfo, RouteDefinition.class);
        log.info(JsonUtils.toJsonStringWithPrettyPrinter(routers));
        dynamicRouteService.save(routers);
        log.info("初始化网关路由结束");
    }

    private String getDataId() {
        // ${prefix}-${spring.profiles.active}.${file-extension}
        StringBuilder dataId = new StringBuilder();
        // Prefix
        dataId.append(StringUtils.isBlank(nacosConfigProperties.getPrefix()) ? appName : nacosConfigProperties.getPrefix());
        // Router
        dataId.append("-router");
        // Profile
        if (StringUtils.isNotBlank(profile)) {
            dataId.append("-").append(profile);
        }
        dataId.append(".json");
        return dataId.toString();
    }

}