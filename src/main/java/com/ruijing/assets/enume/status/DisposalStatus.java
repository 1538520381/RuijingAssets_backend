package com.ruijing.assets.enume.status;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 资产处置状态
 * @email 3161788646@qq.com
 * @date 2022/12/16 11:35
 */

public enum DisposalStatus {
    //[1，拟处置  2，处置中  3，处置完毕  4，待核销  5，已核销  6，作废]

    //1，拟处置
    PROPOSED_DISPOSAL("拟处置", 1),
    //2，处置中
    UNDER_DISPOSAL("处置中", 2),
    //3，处置完毕
    DISPOSAL_COMPLETED("处理完毕", 3),
    //4，待核销
    TO_BE_WRITTEN_OFF(" 待核销", 4),
    //5，已核销
    WRITTEN_OFF("已核销", 5),
    //6，作废
    TO_VOID("作废", 6);


    //状态码
    private final int code;
    //状态消息
    private final String msg;


    //构造方法
    DisposalStatus(String msg, int code) {
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
