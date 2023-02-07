package com.ruijing.assets.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruijing.assets.entity.vo.assetVO.Highlight;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class AssetInsertDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 资产名字
     */
    private String assetName;
    /**
     * 债权本金
     */
    private Long creditRightFare;
    /**
     * 所属省份
     */
    private Integer province;
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
     * 开始日期 格式yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 截至日期 格式yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /*
     *  处置状态[1，拟处置  2，处置中  3，处置完毕  4，待核销  5，已核销  6，作废]
     */
    private Integer DisposalStatus;
    /*
     * 资产亮点
     */
    private List<Highlight> highlights;
    /*
     * 债权种类[1, "金融债权" 2, "非金融债权"]
     */
    private Integer AssetType;
    /*
     * 债权担保人
     */
    private List<GuaranteesDTO> guarantees;
    /*
     * 抵押物
     */
    private List<CollateralDTO> collateral;

}
