package com.ruijing.assets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//开启异步处理
@EnableAsync
public class RuijingAssetsAdminApplication {

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        SpringApplication.run(RuijingAssetsAdminApplication.class, args);
    }

}
