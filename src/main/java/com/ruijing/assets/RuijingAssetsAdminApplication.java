package com.ruijing.assets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@EnableTransactionManagement
@SpringBootApplication
//开启异步处理
@EnableAsync
public class RuijingAssetsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuijingAssetsAdminApplication.class, args);
    }

}
