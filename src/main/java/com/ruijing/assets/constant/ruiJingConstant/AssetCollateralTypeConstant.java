package com.ruijing.assets.constant.ruiJingConstant;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 抵押物种类常量
 * @email 3161788646@qq.com
 * @date 2023/1/27 16:17
 */

@Data
public class AssetCollateralTypeConstant {
    public static Map<Integer, String> assetCollateralTypeMap = null;

    static {
        assetCollateralTypeMap = new HashMap<>();
        assetCollateralTypeMap.put(1, "光地(工业)");
        assetCollateralTypeMap.put(2, "光地(商业)");
        assetCollateralTypeMap.put(3, "光地(商住)");
        assetCollateralTypeMap.put(4, "工业房地产");
        assetCollateralTypeMap.put(5, "住宅");
        assetCollateralTypeMap.put(6, "商业");
        assetCollateralTypeMap.put(7, "办公楼");
        assetCollateralTypeMap.put(8, "在建工程");
        assetCollateralTypeMap.put(9, "机器设备");
        assetCollateralTypeMap.put(10, "林权");
        assetCollateralTypeMap.put(11, "大宗物资");
        assetCollateralTypeMap.put(12, "运输设备");
        assetCollateralTypeMap.put(13, "生产用设");
        assetCollateralTypeMap.put(14, "股权");
    }

}
