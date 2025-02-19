package com.ruijing.assets.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruijing.assets.entity.pojo.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class AssetUpdateDTO extends AssetEntity {

    private List<AssetCollateralEntity> collateral;

    //担保人
    private List<AssetGuaranteeEntity> guarantees;

    private List<AssetHighlight> highlights;
    private List<AssetFile> assetFiles;
}
