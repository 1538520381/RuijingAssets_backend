package com.ruijing.assets.exceptionHandler.springsecurityExceptionHandler;


import com.alibaba.fastjson.JSON;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.util.using.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 鉴权异常处理器
 * @email 3161788646@qq.com
 * @date 2023/1/31 16:40
 */

@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("没有权限访问接口");
        //没有权限访问该接口
        accessDeniedException.printStackTrace();
        R errorResult = R.error(
                RuiJingExceptionEnum.NO_JURISDICTION.getCode()
                , RuiJingExceptionEnum.NO_JURISDICTION.getMsg()
        );
        //响应给前端
        WebUtil.renderString(response, JSON.toJSONString(errorResult));
    }
}
