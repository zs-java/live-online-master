package com.live.online.im;

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
public class ImServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ImServiceApp.class, args);
    }

}
