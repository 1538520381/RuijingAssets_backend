package com.ruijing.assets.constant.ruiJingConstant;


import java.util.HashMap;
import java.util.Map;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 资产诉讼状态常量
 * @email 3161788646@qq.com
 * @date 2022/12/16 17:16
 */

public class LitigationStatusConstant {
    public static Map<Integer, String> litigationStatusMap = null;

    static {
        litigationStatusMap = new HashMap<>();
        litigationStatusMap.put(1, "未诉");
        litigationStatusMap.put(2, "已诉未判决");
        litigationStatusMap.put(3, "已判决未执行");
        litigationStatusMap.put(4, "已执行");
        litigationStatusMap.put(5, "破产");
    }
}
