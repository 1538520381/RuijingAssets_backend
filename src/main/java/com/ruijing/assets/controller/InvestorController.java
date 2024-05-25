package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruijing.assets.entity.dto.InvestorDTO;
import com.ruijing.assets.entity.pojo.*;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Persolute
 * @version 1.0
 * @description Investor Controller
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:37
 */
@RestController
@RequestMapping("/assets/investor")
public class InvestorController {
    @Autowired
    private InvestorService investorService;

    @Autowired
    private InvestorInvestmentTypeService investorInvestmentTypeService;

    @Autowired
    private InvestorInvestmentAmountService investorInvestmentAmountService;

    @Autowired
    private InvestorAssetOperationModelService investorAssetOperationModelService;

    @Autowired
    private InvestorValueAddedServiceService investorValueAddedServiceService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取投资人列表
     * @email 1538520381@qq.com
     * @date 2024/5/7 上午10:18
     */
    @GetMapping
    private R getList(Integer page, Integer pageSize, String name, String type, String intentionRegion, @RequestParam List<Long> investmentAmount) {
        Page<InvestorEntity> investorEntityPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<InvestorEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.equals("null") && !name.isEmpty()) {
            lambdaQueryWrapper.like(InvestorEntity::getName, name);
        }
        if (type != null && !type.equals("null") && !type.isEmpty()) {
            lambdaQueryWrapper.eq(InvestorEntity::getType, Integer.parseInt(type));
        }
        if (intentionRegion != null && !intentionRegion.equals("null") && !intentionRegion.isEmpty()) {
            lambdaQueryWrapper.eq(InvestorEntity::getIntentionRegion, intentionRegion);
        }
        if (investmentAmount != null && !investmentAmount.isEmpty()) {
            Set<Long> set = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().in(InvestorInvestmentAmountEntity::getInvestmentAmountId, investmentAmount)).stream().map(InvestorInvestmentAmountEntity::getInvestorId).collect(Collectors.toSet());
            if (set.isEmpty()) {
                set.add(null);
            }
            lambdaQueryWrapper.in(InvestorEntity::getId, set);
        }
        investorService.page(investorEntityPage, lambdaQueryWrapper);
        List<InvestorEntity> investorEntityList = investorEntityPage.getRecords();
        List<InvestorDTO> investorDTOList = investorEntityList.stream().map(investorEntity -> {
            InvestorDTO investorDTO = new InvestorDTO();
            BeanUtils.copyProperties(investorEntity, investorDTO);

            List<Long> investorInvestmentAmountId = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentAmountEntity::getInvestmentAmountId).collect(Collectors.toList());
            List<Long> investorInvestmentTypeId = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentTypeEntity::getInvestmentTypeId).collect(Collectors.toList());
            List<Long> investorAssetOperationModelId = investorAssetOperationModelService.list(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorAssetOperationModelEntity::getAssetOperationModelId).collect(Collectors.toList());
            List<Long> investorValueAddedServiceId = investorValueAddedServiceService.list(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorValueAddedServiceEntity::getValueAddedServiceId).collect(Collectors.toList());

            investorDTO.setInvestmentAmount(investorInvestmentAmountId);
            investorDTO.setInvestmentType(investorInvestmentTypeId);
            investorDTO.setAssetOperationModel(investorAssetOperationModelId);
            investorDTO.setValueAddedService(investorValueAddedServiceId);

            return investorDTO;
        }).collect(Collectors.toList());
        Page<InvestorDTO> investorDTOPage = new Page<>(page, pageSize);
        BeanUtils.copyProperties(investorEntityPage, investorDTOPage);
        investorDTOPage.setRecords(investorDTOList);
        return R.success("获取成功").put("investor", investorDTOPage);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增投资人
     * @email 1538520381@qq.com
     * @date 2024/5/7 下午7:18
     */
    @PostMapping
    private R addInvestor(@RequestBody InvestorDTO investorDTO) throws Exception {
        InvestorEntity investorEntity = new InvestorEntity();
        BeanUtils.copyProperties(investorDTO, investorEntity);
        investorService.save(investorEntity);

        for (Long investmentTypeId : investorDTO.getInvestmentType()) {
            InvestorInvestmentTypeEntity investorInvestmentType = new InvestorInvestmentTypeEntity();
            investorInvestmentType.setInvestorId(investorEntity.getId());
            investorInvestmentType.setInvestmentTypeId(investmentTypeId);
            investorInvestmentTypeService.save(investorInvestmentType);
        }

        for (Long investmentAmountId : investorDTO.getInvestmentAmount()) {
            InvestorInvestmentAmountEntity investorInvestmentAmount = new InvestorInvestmentAmountEntity();
            investorInvestmentAmount.setInvestorId(investorEntity.getId());
            investorInvestmentAmount.setInvestmentAmountId(investmentAmountId);
            investorInvestmentAmountService.save(investorInvestmentAmount);
        }

        for (Long assetOperationModelId : investorDTO.getAssetOperationModel()) {
            InvestorAssetOperationModelEntity investorAssetOperationModel = new InvestorAssetOperationModelEntity();
            investorAssetOperationModel.setInvestorId(investorEntity.getId());
            investorAssetOperationModel.setAssetOperationModelId(assetOperationModelId);
            investorAssetOperationModelService.save(investorAssetOperationModel);
        }

        for (Long valueAddedServiceId : investorDTO.getValueAddedService()) {
            InvestorValueAddedServiceEntity investorValueAddedService = new InvestorValueAddedServiceEntity();
            investorValueAddedService.setInvestorId(investorEntity.getId());
            investorValueAddedService.setValueAddedServiceId(valueAddedServiceId);
            investorValueAddedServiceService.save(investorValueAddedService);
        }
        return R.success("新增成功");
    }

    @PostMapping("/addList")
    private R addInvestorList(@RequestBody List<InvestorDTO> investorDTOList) throws Exception {
        for (InvestorDTO investorDTO : investorDTOList) {
            addInvestor(investorDTO);
        }
        return R.success("新增成功");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 删除投资人
     * @email 1538520381@qq.com
     * @date 2024/5/7 下午7:31
     */
    @DeleteMapping("/{investorId}")
    public R delete(@PathVariable Long investorId) {
        investorService.removeById(investorId);
        investorInvestmentTypeService.remove(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorId));
        investorValueAddedServiceService.remove(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorId));
        investorAssetOperationModelService.remove(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorId));
        investorInvestmentAmountService.remove(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorId));
        return R.success("删除成功");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 投资人匹配
     * @email 1538520381@qq.com
     * @date 2024/5/24 上午11:42
     */
    @GetMapping("/match/{assetId}")
    public R match(@PathVariable Long assetId) {
        List<InvestorEntity> investorEntityList = investorService.match(assetId);
        List<InvestorDTO> investorDTOList = investorEntityList.stream().map(investorEntity -> {
            InvestorDTO investorDTO = new InvestorDTO();
            BeanUtils.copyProperties(investorEntity, investorDTO);

            List<Long> investorInvestmentAmountId = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentAmountEntity::getInvestmentAmountId).collect(Collectors.toList());
            List<Long> investorInvestmentTypeId = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentTypeEntity::getInvestmentTypeId).collect(Collectors.toList());
            List<Long> investorAssetOperationModelId = investorAssetOperationModelService.list(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorAssetOperationModelEntity::getAssetOperationModelId).collect(Collectors.toList());
            List<Long> investorValueAddedServiceId = investorValueAddedServiceService.list(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorValueAddedServiceEntity::getValueAddedServiceId).collect(Collectors.toList());

            investorDTO.setInvestmentAmount(investorInvestmentAmountId);
            investorDTO.setInvestmentType(investorInvestmentTypeId);
            investorDTO.setAssetOperationModel(investorAssetOperationModelId);
            investorDTO.setValueAddedService(investorValueAddedServiceId);

            return investorDTO;
        }).collect(Collectors.toList());
        return R.success("获取成功").put("investor", investorDTOList);
    }
}
