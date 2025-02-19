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
        assetCollateralTypeMap.put(1, "公寓");
        assetCollateralTypeMap.put(2, "在建工程");
        assetCollateralTypeMap.put(3, "厂房");
        assetCollateralTypeMap.put(4, "酒店");
        assetCollateralTypeMap.put(5, "商业综合体");
        assetCollateralTypeMap.put(6, "办公楼");
        assetCollateralTypeMap.put(7, "住宅");
        assetCollateralTypeMap.put(8, "股权/股票");
        assetCollateralTypeMap.put(9, "收益权");
        assetCollateralTypeMap.put(10, "设备");
        assetCollateralTypeMap.put(11, "矿权");
        assetCollateralTypeMap.put(12, "林权");
        assetCollateralTypeMap.put(13, "机动车");
        assetCollateralTypeMap.put(14, "知识产权");
        assetCollateralTypeMap.put(15, "无形资产");
        assetCollateralTypeMap.put(16, "其他资产");
        assetCollateralTypeMap.put(17, "商铺");
        assetCollateralTypeMap.put(18, "仓储用房");
        assetCollateralTypeMap.put(19, "商业用地");
        assetCollateralTypeMap.put(20, "工业用地");
        assetCollateralTypeMap.put(21, "住宅用地");
        assetCollateralTypeMap.put(22, "耕地");
        assetCollateralTypeMap.put(23, "农业用地");
        assetCollateralTypeMap.put(24, "交通用地");
        assetCollateralTypeMap.put(25, "其他用地");
    }
}
