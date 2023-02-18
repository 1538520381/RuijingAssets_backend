package com.ruijing.assets.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 常用配置类
 * [跨域，分页，格式化]
 * @email 3161788646@qq.com
 * @date 2023/1/27 16:16
 */

@Configuration
public class CommonConfig {

    /**
     * 解决跨域的问题
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /*
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

//    /* ?
//     * 解决日期格式不符合的问题
//     * ?是否有用？ 我也忘了
//     */
//    @Bean
//    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        //日期格式
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        objectMapper.setDateFormat(smt);
//        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//        //编码格式
//        List<MediaType> list = new ArrayList<MediaType>();
//        list.add(MediaType.APPLICATION_JSON_UTF8);
//        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
//        return mappingJackson2HttpMessageConverter;
//    }

}
