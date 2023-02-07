package com.ruijing.assets.exception;


import lombok.Data;

@Data
public class RuiJingException extends RuntimeException {
    //异常信息
    private String msg;
    //状态码
    private Integer code;

    private static final long serialVersionUID = 5565760508056698922L;

    public RuiJingException() {
        super();
    }

    public RuiJingException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
