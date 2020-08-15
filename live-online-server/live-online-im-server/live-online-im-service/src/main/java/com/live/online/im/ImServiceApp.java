package com.live.online.im;

import com.live.online.common.core.lock.annotation.EnableDistributedLock;
import com.live.online.common.core.lock.core.DistributedLockType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 朱帅
 * @date 2020-08-10 1:13 上午
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.live.online")
@EnableDistributedLock(value = DistributedLockType.ZOOKEEPER, basePackage = "com.live.online.im.service.impl")
public class ImServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ImServiceApp.class, args);
    }

}
