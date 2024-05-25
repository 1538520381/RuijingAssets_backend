package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.InvestorDao;
import com.ruijing.assets.entity.pojo.*;
import com.ruijing.assets.service.AssetService;
import com.ruijing.assets.service.InvestorInvestmentAmountService;
import com.ruijing.assets.service.InvestorInvestmentTypeService;
import com.ruijing.assets.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Investor ServiceImpl
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:36
 */
@Service
public class InvestorServiceImpl extends ServiceImpl<InvestorDao, InvestorEntity> implements InvestorService {
    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetCollateralServiceImpl assetCollateralService;

    @Autowired
    private InvestorInvestmentAmountService investorInvestmentAmountService;

    @Autowired
    private InvestorInvestmentTypeService investorInvestmentTypeService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 投资人匹配
     * @email 1538520381@qq.com
     * @date 2024/5/25 下午1:05
     */
    public List<InvestorEntity> match(Long assetId) {
        AssetEntity assetEntity = assetService.getById(assetId);
        List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, assetId));
        LambdaQueryWrapper<InvestorEntity> lambdaQueryWrapper = new LambdaQueryWrapper<InvestorEntity>().eq(InvestorEntity::getIntentionRegion, assetEntity.getRegion());
        List<InvestorEntity> investorEntityList = list(lambdaQueryWrapper);
        for (InvestorEntity investorEntity : investorEntityList) {
            List<InvestorInvestmentAmountEntity> investorInvestmentAmountEntities = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntity.getId()));
            List<Long> investmentAmountList = new ArrayList<>();
            for (InvestorInvestmentAmountEntity investmentAmountEntity : investorInvestmentAmountEntities) {
                investmentAmountList.add(investmentAmountEntity.getInvestmentAmountId());
            }
            if (assetEntity.getCreditRightFare().compareTo(new BigDecimal(10000000)) >= 0 || !investmentAmountList.contains(1L)) {
                if (assetEntity.getCreditRightFare().compareTo(new BigDecimal(10000000)) < 0 || assetEntity.getCreditRightFare().compareTo(new BigDecimal(30000000)) >= 0 || !investmentAmountList.contains(2L)) {
                    if (assetEntity.getCreditRightFare().compareTo(new BigDecimal(30000000)) < 0 || assetEntity.getCreditRightFare().compareTo(new BigDecimal(60000000)) >= 0 || !investmentAmountList.contains(3L)) {
                        if (assetEntity.getCreditRightFare().compareTo(new BigDecimal(60000000)) < 0 || assetEntity.getCreditRightFare().compareTo(new BigDecimal(100000000)) >= 0 || !investmentAmountList.contains(4L)) {
                            if (assetEntity.getCreditRightFare().compareTo(new BigDecimal(100000000)) < 0 || assetEntity.getCreditRightFare().compareTo(new BigDecimal(300000000)) >= 0 || !investmentAmountList.contains(5L)) {
                                if (assetEntity.getCreditRightFare().compareTo(new BigDecimal(300000000)) < 0) {
                                    investorEntityList.remove(investorEntity);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
            List<InvestorInvestmentTypeEntity> investorInvestmentTypeEntities = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntity.getId()));
            List<Long> investmentTypeList = new ArrayList<>();
            for (InvestorInvestmentTypeEntity investorInvestmentType : investorInvestmentTypeEntities) {
                investmentTypeList.add(investorInvestmentType.getInvestmentTypeId());
            }
            boolean flag = false;
            for (AssetCollateralEntity assetCollateralEntity : assetCollateralEntities) {
                if (investmentTypeList.contains(Long.valueOf(assetCollateralEntity.getCollateralType()))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                investorEntityList.remove(investorEntity);
            }
        }
        return investorEntityList;
    }
}
