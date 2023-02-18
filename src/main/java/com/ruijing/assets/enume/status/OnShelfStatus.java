package com.ruijing.assets.enume.status;



/**
 * @description: 资产上架状态枚举类
 * @email 3161788646@qq.com
 * @author K0n9D1KuA
 * @date 2022/12/16 11:08
 * @version 1.0
 */

public enum OnShelfStatus {
    //上架状态[0，删除 1，待上架 2，已上架 3，已下架]
    //0，逻辑删除
    LOGIC_DELETE("作废", 0),
    //1，待上架
    TO_BE_PUT_ON_THE_SHELF("待上架", 1),
    //2，已上架
    ON_THE_SHELF("已上架", 2),
    //3，已下架
    OFF_SHELL("已下架", 3);


    //状态码
    private final int code;
    //状态消息
    private final String msg;

    //构造方法
    OnShelfStatus(String msg, int code) {
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
