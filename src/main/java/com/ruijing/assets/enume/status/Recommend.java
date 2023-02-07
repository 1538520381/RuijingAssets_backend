package com.ruijing.assets.enume.status;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 资产是否重点推介
 * @email 3161788646@qq.com
 * @date 2022/12/16 11:09
 */

public enum Recommend {
    //是否重点推介[0，否 1，是]
    CAN_RECOMMEND("是", 1),
    NOT_RECOMMEND("否", 0);

    //状态码
    private final int code;
    //状态消息
    private final String msg;

    //构造方法
    Recommend(String msg, int code) {
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
