package com.ruijing.assets.util.using;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: Spring容器工具类
 * 1,获得spring容器
 * @email 3161788646@qq.com
 * @date 2023/1/28 20:44
 */

@Component
@Data
public class ApplicationContextUtil implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    private static ApplicationContext APPLICATION_CONTEXT;


    @Override
    public void afterPropertiesSet() throws Exception {
        APPLICATION_CONTEXT = applicationContext;
    }

    //返回spring容器
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
}
