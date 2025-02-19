package com.ruijing.assets.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.AssetFile;
import com.ruijing.assets.entity.vo.assetVO.Highlight;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class AssetAddDTO extends AssetEntity {
    private List<Highlight> highlights;

    private List<GuaranteesDTO> guarantees;
    /*
     * 抵押物
     */
    private List<CollateralDTO> collateral;

    private List<AssetFile> assetFiles;

}
