package com.live.online.common.zookeeper.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Zookeeper 配置类
 * @author 朱帅
 */
@Data
@Configuration
public class ZookeeperConfiguration {

    @Value("${zk.url:}")
    private String zkUrl;

}