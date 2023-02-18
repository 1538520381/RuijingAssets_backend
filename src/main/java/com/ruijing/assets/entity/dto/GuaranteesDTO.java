package com.ruijing.assets.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class GuaranteesDTO {
    /**
     * 担保人名字
     */
    private String guaranteeName;
    /**
     * 担保方式
     */
    private Integer method;

    /**
     * 担保方式 冗余字段
     */
    private String methodString;
}
