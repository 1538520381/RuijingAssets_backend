package com.ruijing.assets.enume.status;

public enum UserStatus {

    NORMAL_STATUS("正常使用", 1),
    FORBIDDEN_STATUS("已禁用", 0);
    //状态码
    private final int code;
    //状态消息
    private final String msg;

    //构造方法
    UserStatus(String msg, int code) {
        this.code = code;
        this.msg = msg;
    }

    //getOpenID / set

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
