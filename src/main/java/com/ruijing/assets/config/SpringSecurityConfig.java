package com.ruijing.assets.config;


import com.ruijing.assets.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: SpringSecurity的配置类
 * @email 3161788646@qq.com
 * @date 2023/1/31 15:11
 */

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    //过滤器
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    //处理认证失败异常处理器
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
    //处理授权失败异常处理器
    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    //加密组件
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers(
                        "/assets/customer/login"
                        , "/assets/customer/register"
                        , "/assets/carouselchart/uploadCarouselChart"
                        , "/assets/asset/upload/*"
                        , "/assets/contact_us/updateImage"
                        , "/assets/asset/upload/file/*").anonymous()
                // TODO 其他接口后续认证
                // 除上面外的所有请求全部需要登录
                .anyRequest().authenticated();


        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);


        //把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        //拦截器的位置处于最前面
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout().disable();

        //允许跨域
        http.cors();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
