package com.ruijing.assets.enume.status;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 资产处理方式状态枚举类
 * @email 3161788646@qq.com
 * @date 2022/12/16 11:08
 */

public enum DisposalMethodStatus {
    //1，折扣变现
    DISCOUNT_REALIZATION("折扣变现", 1),
    //2，债务更新
    DEBT_RENEWAL("债务更新", 2),
    //3，以资抵债
    REPAYMENT_OF_DEBTS_WITH_ASSETS("以资抵债", 3),
    //4，资产置换
    ASSET_REPLACEMENT("资产置换", 4),
    //5，收益权转让
    TRANSFER_OF_USUFRUCT("收益权转让", 5),
    //6，破产清算
    BANKRUPTCY_LIQUIDATION("破产清算", 6),
    //7，以物抵债
    TO_PAY_DEBTS_IN_KIND("以物抵债", 7),
    //8，委托处置
    ENTRUSTED_DISPOSAL("委托处置", 8),
    //9，其他方式
    OTHER_METHOD("其他方式", 9);


    //处置方式
    // [1，折扣变现 2，债务更新
    // 3，以资抵债 4，资产置换
    // 5，收益权转让 6，破产清算
    // 7，以物抵债 8，委托处置 9，其他方式]

    //状态码
    private final int code;
    //状态消息
    private final String msg;


    //构造方法
    DisposalMethodStatus(String msg, int code) {
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
