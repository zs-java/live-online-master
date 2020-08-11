package com.live.online.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 朱帅
 * @date 2020-08-10 12:32 上午
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.live.online")
@EnableFeignClients("com.live.online")
public class UserServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }

}
