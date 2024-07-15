package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.InvestorIntentionRegionDao;
import com.ruijing.assets.dao.TraceDao;
import com.ruijing.assets.entity.dto.AssetDto;
import com.ruijing.assets.entity.dto.InvestorDTO;
import com.ruijing.assets.entity.dto.TraceDto;
import com.ruijing.assets.entity.dto.TraceInvestorAssetDto;
import com.ruijing.assets.entity.pojo.*;
import com.ruijing.assets.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/14 18:52
 */
@Service
public class TraceServiceImpl extends ServiceImpl<TraceDao, TraceEntity> implements TraceService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private InvestorService investorService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetCollateralService assetCollateralService;

    @Autowired
    private InvestorInvestmentAmountService investorInvestmentAmountService;

    @Autowired
    private InvestorInvestmentTypeService investorInvestmentTypeService;

    @Autowired
    private InvestorIntentionRegionService investorIntentionRegionService;

    @Override
    public List<TraceDto> getAllByUser(Long userId) {
        LambdaQueryWrapper<TraceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        if (!sysUserEntity.getAdmin()) {
            lambdaQueryWrapper.eq(TraceEntity::getUserId, userId);
        }
        lambdaQueryWrapper.orderByDesc(TraceEntity::getDate);
        List<TraceEntity> list = list(lambdaQueryWrapper);
        Set<String> set = new HashSet<>();
        List<TraceDto> list1 = list.stream().map(item -> {
            if (!set.contains(item.getInvestorId() + "-" + item.getAssetId())) {
                set.add(item.getInvestorId() + "-" + item.getAssetId());
                TraceDto traceDto = new TraceDto();
                BeanUtils.copyProperties(item, traceDto);
                AssetEntity assetEntity = assetService.getById(item.getAssetId());
                SysUserEntity sysUserEntity1 = sysUserService.getById(assetEntity.getCreateUser());
                traceDto.setCreateAssetName(sysUserEntity1.getName());
                traceDto.setInvestorName(investorService.getById(item.getInvestorId()).getName());
                traceDto.setAssetUserName(assetEntity.getName());
                traceDto.setAssetName(assetEntity.getAssetName());
                SysUserEntity sysUserEntity2 = sysUserService.getById(investorService.getById(item.getInvestorId()).getCreateUser());
                traceDto.setCreateInvestorName(sysUserEntity2.getName());
                return traceDto;
            } else {
                return null;
            }
        }).collect(Collectors.toList());
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) == null) {
                list1.remove(i);
                i--;
            }
        }
        return list1;
    }

    @Override
    public List<TraceDto> get(Long investorId, Long assetId, Long userId) {
        LambdaQueryWrapper<TraceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (investorId != null) {
            lambdaQueryWrapper.eq(TraceEntity::getInvestorId, investorId);
        }
        if (assetId != null) {
            lambdaQueryWrapper.eq(TraceEntity::getAssetId, assetId);
        }
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        if (!sysUserEntity.getAdmin()) {
            lambdaQueryWrapper.eq(TraceEntity::getUserId, userId);
        }
        lambdaQueryWrapper.orderByDesc(TraceEntity::getDate);
        lambdaQueryWrapper.ne(TraceEntity::getType, 0);
        List<TraceEntity> list = list(lambdaQueryWrapper);
        List<TraceDto> list1 = list.stream().map(item -> {
            TraceDto traceDto = new TraceDto();
            BeanUtils.copyProperties(item, traceDto);
            SysUserEntity sysUserEntity1 = sysUserService.getById(item.getUserId());
            traceDto.setUserName(sysUserEntity1.getName());
            return traceDto;
        }).collect(Collectors.toList());
        return list1;
    }

    //    @Override
//    public List<TraceInvestorAssetDto> getUnend(Long userId) {
//        LambdaQueryWrapper<TraceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        SysUserEntity sysUserEntity = sysUserService.getById(userId);
//        if (!sysUserEntity.getAdmin()) {
//            lambdaQueryWrapper.eq(TraceEntity::getUserId, userId);
//        }
//        lambdaQueryWrapper.ne(TraceEntity::getType, 2);
//        lambdaQueryWrapper.ne(TraceEntity::getType, 3);
//        List<TraceEntity> list = list(lambdaQueryWrapper);
//        List<TraceInvestorAssetDto> list1 = list.stream().map(item -> {
//            TraceInvestorAssetDto traceInvestorAssetDto = new TraceInvestorAssetDto();
//
//            traceInvestorAssetDto.setTrace(item);
//
//            InvestorEntity investorEntity = investorService.getById(item.getInvestorId());
//            InvestorDTO investorDTO = new InvestorDTO();
//            BeanUtils.copyProperties(investorEntity, investorDTO);
//            List<Long> investorInvestmentAmountId = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentAmountEntity::getInvestmentAmountId).collect(Collectors.toList());
//            List<Long> investorInvestmentTypeId = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentTypeEntity::getInvestmentTypeId).collect(Collectors.toList());
//            List<Long> investorAssetOperationModelId = investorAssetOperationModelService.list(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorAssetOperationModelEntity::getAssetOperationModelId).collect(Collectors.toList());
//            List<Long> investorValueAddedServiceId = investorValueAddedServiceService.list(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorValueAddedServiceEntity::getValueAddedServiceId).collect(Collectors.toList());
//            List<String> investorIntentionRegion = investorIntentionRegionService.list(new LambdaQueryWrapper<InvestorIntentionRegionEntity>().eq(InvestorIntentionRegionEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorIntentionRegionEntity::getValue).collect(Collectors.toList());
//            investorDTO.setInvestmentAmount(investorInvestmentAmountId);
//            investorDTO.setInvestmentType(investorInvestmentTypeId);
//            investorDTO.setAssetOperationModel(investorAssetOperationModelId);
//            investorDTO.setValueAddedService(investorValueAddedServiceId);
//            investorDTO.setIntentionRegion(investorIntentionRegion);
//            traceInvestorAssetDto.setInvestorDTO(investorDTO);
//
//            AssetEntity assetEntity = assetService.getById(item.getAssetId());
//            AssetDto assetDto = new AssetDto();
//            BeanUtils.copyProperties(assetEntity, assetDto);
//            List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, assetEntity.getId()));
//            List<AssetGuaranteeEntity> assetGuaranteeEntities = assetGuaranteeService.list(new LambdaQueryWrapper<AssetGuaranteeEntity>().eq(AssetGuaranteeEntity::getAssetId, assetEntity.getId()));
//            List<AssetHighlight> assetHighlights = assetHighlightService.list(new LambdaQueryWrapper<AssetHighlight>().eq(AssetHighlight::getAssetId, assetEntity.getId()));
//            assetDto.setAssetCollateralEntities(assetCollateralEntities);
//            assetDto.setAssetGuaranteeEntities(assetGuaranteeEntities);
//            assetDto.setAssetHighlights(assetHighlights);
//            traceInvestorAssetDto.setAssetDto(assetDto);
//
//            return traceInvestorAssetDto;
//        }).collect(Collectors.toList());
//        return list1;
//    }
    @Override
    public List<TraceDto> getUnend(Long userId) {
        LambdaQueryWrapper<TraceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        if (!sysUserEntity.getAdmin()) {
            lambdaQueryWrapper.eq(TraceEntity::getUserId, userId);
        }
        lambdaQueryWrapper.ne(TraceEntity::getType, 2);
        lambdaQueryWrapper.ne(TraceEntity::getType, 3);
        lambdaQueryWrapper.orderByDesc(TraceEntity::getDate);
        List<TraceEntity> list = list(lambdaQueryWrapper);
        Set<String> set = new HashSet<>();
        List<TraceDto> list1 = list.stream().map(item -> {
            if (!set.contains(item.getInvestorId() + "-" + item.getAssetId())) {
                set.add(item.getInvestorId() + "-" + item.getAssetId());
                TraceDto traceDto = new TraceDto();
                BeanUtils.copyProperties(item, traceDto);
                AssetEntity assetEntity = assetService.getById(item.getAssetId());
                traceDto.setAssetName(assetEntity.getAssetName());
                SysUserEntity sysUserEntity1 = sysUserService.getById(assetEntity.getCreateUser());
                traceDto.setCreateAssetName(sysUserEntity1.getName());
                traceDto.setInvestorName(investorService.getById(item.getInvestorId()).getName());
                traceDto.setAssetUserName(assetEntity.getName());
                SysUserEntity sysUserEntity2 = sysUserService.getById(investorService.getById(item.getInvestorId()).getCreateUser());
                traceDto.setCreateInvestorName(sysUserEntity2.getName());
                return traceDto;
            } else {
                return null;
            }
        }).collect(Collectors.toList());
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) == null) {
                list1.remove(i);
                i--;
            }
        }
        return list1;
    }

    @Override
    public Long addByUnique(TraceEntity traceEntity) {
        LambdaQueryWrapper<TraceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TraceEntity::getInvestorId, traceEntity.getInvestorId());
        lambdaQueryWrapper.eq(TraceEntity::getAssetId, traceEntity.getAssetId());
        List<TraceEntity> traceEntities = list(lambdaQueryWrapper);
        if (traceEntities.isEmpty()) {
            save(traceEntity);
            return traceEntity.getId();
        }
        return traceEntities.get(0).getId();
    }

    @Override
    public List<TraceDto> match(Long assetId, Long userId) {
        AssetEntity assetEntity = assetService.getById(assetId);
        List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, assetId));
        List<InvestorEntity> investorEntityList = investorService.list();
        for (int i = 0;i < investorEntityList.size(); i++) {
            List<InvestorIntentionRegionEntity> investorIntentionRegionEntities = investorIntentionRegionService.list(new LambdaQueryWrapper<InvestorIntentionRegionEntity>().eq(InvestorIntentionRegionEntity::getInvestorId,investorEntityList.get(i).getId()));
            boolean flag0 = false;
            for (InvestorIntentionRegionEntity investorIntentionRegion: investorIntentionRegionEntities) {
                if(investorIntentionRegion.getValue().equals(assetEntity.getRegion())){
                    flag0 = true;
                    break;
                }
            }
            if (!flag0){
                investorEntityList.remove(i);
                i--;
                continue;
            }

            List<InvestorInvestmentAmountEntity> investorInvestmentAmountEntities = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntityList.get(i).getId()));
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
                                    investorEntityList.remove(i);
                                    i--;
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
            List<InvestorInvestmentTypeEntity> investorInvestmentTypeEntities = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntityList.get(i).getId()));
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
                investorEntityList.remove(i);
                i--;
            }
        }
        List<TraceDto> list = new ArrayList<>();
        for (InvestorEntity investorEntity : investorEntityList) {
            TraceEntity trace = new TraceEntity();
            trace.setInvestorId(investorEntity.getId());
            trace.setAssetId(assetId);
            trace.setType(0);
            trace.setUserId(userId);
            Long l = addByUnique(trace);
            TraceDto traceDto = new TraceDto();
            traceDto.setId(l);
            traceDto.setInvestorId(investorEntity.getId());
            traceDto.setInvestorName(investorEntity.getName());
            SysUserEntity sysUserEntity1 = sysUserService.getById(investorEntity.getCreateUser());
            traceDto.setCreateInvestorName(sysUserEntity1.getName());
            traceDto.setAssetId(assetId);
            traceDto.setAssetName(assetEntity.getAssetName());
            traceDto.setAssetUserName(assetEntity.getName());
            SysUserEntity sysUserEntity2 = sysUserService.getById(assetEntity.getCreateUser());
            traceDto.setCreateAssetName(sysUserEntity2.getName());
            list.add(traceDto);
        }
        return list;

    }
}
