package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Persolute
 * @version 1.0
 * @description 投资人
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:31
 */
@Data
@TableName("investor")
public class InvestorEntity {
    // 主键
    @TableId
    private Long id;

    private String operator;

    // 投资人名称
    private String name;

    // 投资人属性
    private String type;

    // 证件号码
    private String certificateId;

    // 投资人所在地
    private String location;

    // 规模大小
    private String scale;

    // 经济类型
    private String economicType;

    // 联系方式
    private String contact;

    private String remarks;

    // 创建用户
    private Long createUser;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
