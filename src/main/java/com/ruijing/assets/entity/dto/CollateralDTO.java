package com.ruijing.assets.entity.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CollateralDTO {
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
    /**
     * 抵押物类型 文字 前端不好处理 冗余字段
     */
    private String collateralTypeString;
}
