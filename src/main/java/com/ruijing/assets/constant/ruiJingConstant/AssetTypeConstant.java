package com.ruijing.assets.constant.ruiJingConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 债权种类常量
 * @email 3161788646@qq.com
 * @date 2023/1/29 1:33
 */

public class AssetTypeConstant {
    public final static Map<Integer, String> assetTypeConstantMap = new HashMap<>();

    static {
        assetTypeConstantMap.put(1, "金融债权");
        assetTypeConstantMap.put(2, "非金融债权");
    }
}
