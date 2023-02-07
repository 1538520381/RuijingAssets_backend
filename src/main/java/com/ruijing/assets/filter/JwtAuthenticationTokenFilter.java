package com.ruijing.assets.filter;

import com.alibaba.fastjson.JSON;
import com.ruijing.assets.constant.redisConstant.RedisKeyConstant;
import com.ruijing.assets.entity.springsecurityentity.ContextUserInfo;
import com.ruijing.assets.entity.springsecurityentity.LoginUser;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.util.using.JwtUtil;
import com.ruijing.assets.util.using.WebUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description:
 * 过滤链：
 * 1，通过解析token,将redis里面的用户信息封装到SpringSecurityUtil中
 * 2，控制接口的访问权限
 * @email 3161788646@qq.com
 * @date 2023/2/6 4:22
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //说明该接口不需要登录  直接放行

            filterChain.doFilter(request, response);
            return;
        }
        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
            String uuid = claims.getSubject();
            //从redis中获取用户信息
            String jsonString = stringRedisTemplate.opsForValue().get(RedisKeyConstant.LOGIN_USER_KEY + uuid);
            //反序列化为对象
            ContextUserInfo contextUserInfo = JSON.parseObject(jsonString, ContextUserInfo.class);
            //存入SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    new LoginUser(contextUserInfo), null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //放行
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            R errorResult = R.error(RuiJingExceptionEnum.INVALID_TOKEN.getCode()
                    , RuiJingExceptionEnum.INVALID_TOKEN.getMsg());
            WebUtil.renderString(response, JSON.toJSONString(errorResult));
        }
    }
}
