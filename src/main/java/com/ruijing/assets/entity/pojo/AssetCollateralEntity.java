package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 房屋债权抵押物
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@Data
@TableName("asset_collateral")
public class AssetCollateralEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 资产id
     */
    private Long assetId;
    /**
     * 抵押物坐落
     */
    private String location;
    /**
     * 抵押物描述
     */
    private String description;
    /**
     * 抵押物面积，保留两位小数
     */
    private BigDecimal area;
    /**
     * 抵押物名字
     */
    private String assetName;
    /**
     * 抵押物类型
     */
    private Integer collateralType;


    @TableField(exist = false)
    /**
     * 抵押物字段 文字类型
     */
    private String collateralTypeString;

}
