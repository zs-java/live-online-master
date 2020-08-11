package com.live.online.user.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 朱帅
 * @date 2020-08-10 12:43 上午
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.live.online")
@ComponentScan("com.live.online")
public class UserWebApp {

    public static void main(String[] args) {
        SpringApplication.run(UserWebApp.class, args);
    }

}
