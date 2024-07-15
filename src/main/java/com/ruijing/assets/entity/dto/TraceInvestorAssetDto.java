package com.ruijing.assets.entity.dto;

import com.ruijing.assets.entity.pojo.TraceEntity;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/21 14:35
 */
@Data
public class TraceInvestorAssetDto {
    public TraceEntity trace;
    private InvestorDTO investorDTO;
    private AssetDto assetDto;
}
