package com.ruijing.assets.entity.vo.assetVO;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class AssetVoInQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
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
     * 所属省份
     */
    private String provinceString;
    /**
     * 收藏量
     */
    private Long collection;
    /**
     * 浏览量
     */
    private Long browse;
    /*
     * 图片
     */
    private String image;
    /*
     * 开始时间
     */
    private Date startTime;
    /*
     * 结束时间
     */
    private Date endTime;
}
