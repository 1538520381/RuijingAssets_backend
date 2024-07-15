package com.ruijing.assets.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruijing.assets.entity.pojo.AssetCollateralEntity;
import com.ruijing.assets.entity.pojo.AssetGuaranteeEntity;
import com.ruijing.assets.entity.pojo.AssetHighlight;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/14 14:36
 */
@Data
public class AssetDto {
    /**
     * 主键
     */
    @TableId
    private Long id;
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
    private Boolean OperatingState;
    /**
     * 债券本息
     */
    private BigDecimal bondPrincipalInterest;
    /**
     * 拟转让金额
     */
    private BigDecimal transferredAmount;
    /**
     * 资产名字
     */
    private String assetName;
    /**
     * 债权本金
     */
    private BigDecimal creditRightFare;
    /**
     * 资产所在地址
     */
    private String address;
    /**
     * 债权银行
     */
    private String creditRightBank;
    /**
     * 诉讼状态[1,未诉 2，已诉未判决 3，已判决未执行 4，已执行 5，破产]
     */
    private Integer litigationStatus;
    /**
     * 处置方式[1，折扣变现 2，债务更新 3，以资抵债 4，资产置换 5，收益权转让 6，破产清算 7，以物抵债 8，委托处置 9，其他方式]
     */
    private Integer disposalMethod;
    /**
     * 管辖法院
     */
    private String competentCourt;
    /**
     * 上架状态[0，删除 1，待上架 2，已上架 3，已下架]
     */
    private Integer onShelfStatus;
    /**
     * 是否重点推介[0，否 1，是]
     */
    private Integer recommend;
    /**
     * 所属省份
     */
    private Integer province;
    /**
     * 开始日期 格式yyyy-MM-dd
     */
    private Date startTime;
    /**
     * 截至日期 格式yyyy-MM-dd
     */
    private Date endTime;

    /*
     *  处置状态[1，拟处置  2，处置中  3，处置完毕  4，待核销  5，已核销  6，作废]
     */
    private Integer DisposalStatus;

    /*
     * 债权种类[1, "金融债权" 2, "非金融债权"]
     */
    private Integer assetType;

    /*
     * 资产排序顺序
     */
    private Integer position;

    /**
     * 创建用户
     */
    private Long createUser;

    private String createUserName;

    private List<AssetCollateralEntity> assetCollateralEntities;

    private List<AssetGuaranteeEntity> assetGuaranteeEntities;

    private List<AssetHighlight> assetHighlights;

    private LocalDateTime createTime;
}
