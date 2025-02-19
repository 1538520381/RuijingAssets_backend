package com.ruijing.assets.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruijing.assets.entity.pojo.*;
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
public class AssetDto extends AssetEntity {

    private List<AssetCollateralEntity> assetCollateralEntities;

//    private List<AssetGuaranteeEntity> assetGuaranteeEntities;
//
//    private List<AssetHighlight> assetHighlights;

    private List<AssetFile> assetFiles;
}
