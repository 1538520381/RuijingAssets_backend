package com.ruijing.assets.exceptionHandler;


import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RuiJingExceptionHandler {

    //瑞京相关业务异常
    @ExceptionHandler(RuiJingException.class)
    public R handlerRuiJingException(RuiJingException ruiJingException) {
        ruiJingException.printStackTrace();
        log.error("出现异常，异常信息 ：{} , 异常状态码 ：{}", ruiJingException.getMsg(), ruiJingException.getCode());
        return R.error(ruiJingException.getCode(), ruiJingException.getMsg());
    }




//    @ExceptionHandler(Exception.class)
//    public R handlerException(Exception e) {
//        e.printStackTrace();
//        //系统未知异常
//        return R.error(RuiJingExceptionEnum.UNCLEAR_EXCEPTION.getCode(),
//                RuiJingExceptionEnum.UNCLEAR_EXCEPTION.getMsg()
//        );
//    }
}
