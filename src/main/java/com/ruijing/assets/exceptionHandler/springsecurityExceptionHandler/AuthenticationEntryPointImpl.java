package com.ruijing.assets.exceptionHandler.springsecurityExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.util.using.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 认证异常处理器
 * @email 3161788646@qq.com
 * @date 2023/1/31 16:40
 */

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    //处理登录出现的异常
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        R errorResult = null;
        if (authException instanceof
                BadCredentialsException) {
            log.error("账号或者密码错误.......");
            authException.printStackTrace();
            //账号或者密码错误
            errorResult = R.error(
                    RuiJingExceptionEnum.PASSWORD_OR_USERNAME_WRONG.getCode()
                    , RuiJingExceptionEnum.PASSWORD_OR_USERNAME_WRONG.getMsg()
            );
        } else if (authException instanceof InsufficientAuthenticationException) {
            log.error("需要登录才能访问该接口.......");
            authException.printStackTrace();
            //需要登录后才能访问特定接口
            errorResult = R.error(RuiJingExceptionEnum.NEED_LOGIN.getCode(),
                    RuiJingExceptionEnum.NEED_LOGIN.getMsg());
        } else {
            log.error("其他原因导致登录失败.......");
            authException.printStackTrace();
            //其他原因导致登录失败
            errorResult = R.error(RuiJingExceptionEnum.LOGIN_FAILED.getCode(),
                    RuiJingExceptionEnum.LOGIN_FAILED.getMsg());
        }
        //响应给前端
        WebUtil.renderString(response, JSON.toJSONString(errorResult));
    }
}

