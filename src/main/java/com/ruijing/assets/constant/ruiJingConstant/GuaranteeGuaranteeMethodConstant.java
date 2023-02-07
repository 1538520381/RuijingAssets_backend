package com.ruijing.assets.constant.ruiJingConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 担保人担保方式常量
 * @email 3161788646@qq.com
 * @date 2022/12/16 16:13
 */

public class GuaranteeGuaranteeMethodConstant {
    public static Map<Integer, String> guaranteeGuaranteeMethodMap = null;

    static {
        guaranteeGuaranteeMethodMap = new HashMap<>();
        guaranteeGuaranteeMethodMap.put(1, "一般担保");
        guaranteeGuaranteeMethodMap.put(2, "连带责任担保");
    }
}
