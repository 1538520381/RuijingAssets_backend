package com.ruijing.assets.aspect;

import com.ruijing.assets.annotation.SysLog;
import com.ruijing.assets.entity.pojo.SysLogEntity;
import com.ruijing.assets.entity.event.SysLogEvent;
import com.ruijing.assets.util.using.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 日志aop切面类
 * 自动异步操作记录日志
 * @email 3161788646@qq.com
 * @date 2023/1/9 22:25
 */

@Aspect
@Component
@Slf4j
public class SysLogAspect {
    @Autowired
    private ApplicationContext applicationContext;


    //切点
    @Pointcut("@annotation(com.ruijing.assets.annotation.SysLog)")
    public void pt() {

    }

    /**
     * 环绕通知
     */
    @Around("pt()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            SysLogEntity systemLogEntity = new SysLogEntity();
            //前置处理
            before(joinPoint, systemLogEntity);
            //ps:
            //这里需要将异常往外抛  会被自定义的异常处理器处理掉
            ret = joinPoint.proceed();
            //后置处理
            ret(ret, systemLogEntity);
        } finally {
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    //前置操作
    private void before(ProceedingJoinPoint joinPoint, SysLogEntity sysLogEntity) {
        //访问时间
        sysLogEntity.setVisitTime(new Date());
        //获得request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        //获取加在方法上的注解
        SysLog sysLog = getSysLog(joinPoint);
        //如果是登录或者登出接口
        String userName = null;
        if (sysLog.operationType() == 1) {
            //此时的用户名是在url上面 userName=xxx
            userName = request.getParameter("userName");
        } else {
            //如果不是登录接口
            //那么用户名在封装的springsecurity中可以拿到
            userName = SpringSecurityUtil.getUserName();
        }
        //设置用户名
        sysLogEntity.setUserName(userName);
        //设置接口操作名字
        sysLogEntity.setOperationName(sysLog.operationName());
        //设置ip
        sysLogEntity.setIp(request.getRemoteHost());
    }

    //后置处理 可以拿到方法返回的结果
    private void ret(Object ret, SysLogEntity sysLogEntity) {
        //来自于ip : 127.0.0.1的 admin 用户于: Wed Feb 01 16:15:26 CST 2023 时刻进行了 : 登录 操作
        // 日志输出
        log.info("来自于ip: {} 的 {} 用户于: {} 时刻进行了: {} 操作",
                sysLogEntity.getIp(),
                sysLogEntity.getUserName(),
                sysLogEntity.getVisitTime(),
                sysLogEntity.getOperationName());
        //发布事件 通知异步更新日志
        applicationContext.publishEvent(new SysLogEvent(this, sysLogEntity));
        log.info("主线程结束了.............");
    }


    //获得方法上的 SysLog 注解
    private SysLog getSysLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SysLog.class);
    }


}
