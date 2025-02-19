package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 房屋资产基本信息表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@Data
@TableName("asset")
public class AssetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;

    private String operator;
    /**
     * 债务人名称
     */
    private String name;
    /**
     * 债务人属性
     */
    private Integer type;
    /**
     * 证件号码
     */
    private String certificateId;
    /**
     * 省份区域
     */
    private String region;
    /**
     * 行业
     */
    private String profession;
    /**
     * 规模
     */
    private Integer scale;
    /**
     * 经营状态
     */
    private Boolean operatingState;
    /**
     * 债权本金
     */
    private BigDecimal creditRightFare;
    /**
     * 债券本息
     */
    private BigDecimal bondPrincipalInterest;
    /**
     * 拟转让金额
     */
    private BigDecimal transferredAmount;
    private String creditRightBank;
    private Integer assetType;
    private String competentCourt;
    private String litigationStatus;
    private String disposalMethod;
    private Integer recommend;
    private Integer onShelfStatus;
    private Long createUser;

    private String guarantee;
    private String highlight;

    private String overseas;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
