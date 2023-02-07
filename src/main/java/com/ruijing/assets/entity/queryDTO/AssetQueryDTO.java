package com.ruijing.assets.entity.queryDTO;


import lombok.Data;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 封装Asset相关的查询条件
 * @email 3161788646@qq.com
 * @date 2022/12/16 21:28
 */

@Data
public class AssetQueryDTO {
    //搜索关键词
    private String key;
    /**
     * 抵押物类型
     */
    private Integer collateralType;
    /**
     * 债权本金 下限
     */
    private Long creditRightFareMin;
    /**
     * 债权本金 上限
     */
    private Long creditRightFareMax;
    /**
     * 所属省份
     */
    private Integer province;
    /*
     * 债权种类[1, "金融债权" 2, "非金融债权"]
     */
    private Integer assetType;

}

