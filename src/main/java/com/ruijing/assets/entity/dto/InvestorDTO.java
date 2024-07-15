package com.ruijing.assets.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruijing.assets.entity.pojo.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/05/07 15:48
 */
@Data
public class InvestorDTO {
    // 主键
    @TableId
    private Long id;

    private String operator;

    // 投资人名称
    private String name;

    // 投资人属性
    private Integer type;

    // 证件号码
    private String certificateId;

    // 投资人所在地
    private String location;

    // 规模大小
    private Integer scale;

    // 联系方式
    private String contact;

    // 经济类型id
    private Long economicTypeId;

    // 拟投资类型
    private List<Long> investmentType;

    // 拟投资金额
    private List<Long> investmentAmount;

    // 资产运营模式
    private List<Long> assetOperationModel;

    // 增值服务
    private List<Long> valueAddedService;

    // 意向投资区域
    private List<String> intentionRegion;

    // 创建用户
    private Long createUser;

    private String createUserName;

    private LocalDateTime createTime;
}
