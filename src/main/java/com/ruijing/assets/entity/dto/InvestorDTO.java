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
public class InvestorDTO extends InvestorEntity {
    public List<InvestorIntentionRegionEntity> investorIntentionRegionEntities;
    public List<InvestorInvestmentTypeEntity> investorInvestmentTypeEntities;
    public List<InvestorInvestmentAmountEntity> investorInvestmentAmountEntities;
    public List<InvestorAssetOperationModelEntity> investorAssetOperationModelEntities;
    public List<InvestorValueAddedServiceEntity> investorValueAddedServiceEntities;
    public List<InvestorContacts> investorContacts;
    public List<InvestmentPositioning> investmentPositioningEntities;
}
