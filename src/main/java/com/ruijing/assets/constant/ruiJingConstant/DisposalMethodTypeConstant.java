package com.ruijing.assets.constant.ruiJingConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 处置方式常量
 * @email 3161788646@qq.com
 * @date 2023/1/27 16:18
 */

public class DisposalMethodTypeConstant {
    public static Map<Integer, String> disposalMethodType = new HashMap<>();

    static {
        disposalMethodType.put(1, "折扣变现");
        disposalMethodType.put(2, "债务更新");
        disposalMethodType.put(3, "以资抵债");
        disposalMethodType.put(4, "资产置换");
        disposalMethodType.put(5, "收益权转让");
        disposalMethodType.put(6, "破产清算");
        disposalMethodType.put(7, "以物抵债");
        disposalMethodType.put(8, "委托处置");
        disposalMethodType.put(9, "诉讼追偿");
        disposalMethodType.put(10, "其他方式");
    }
}
