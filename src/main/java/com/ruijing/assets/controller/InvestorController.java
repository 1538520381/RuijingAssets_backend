package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruijing.assets.dao.InvestorIntentionRegionDao;
import com.ruijing.assets.entity.dto.InvestorDTO;
import com.ruijing.assets.entity.dto.TraceDto;
import com.ruijing.assets.entity.pojo.*;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.*;
import com.ruijing.assets.service.impl.SysUserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
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

    @Autowired
    private InvestorIntentionRegionService investorIntentionRegionService;
    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private TraceService traceService;
    @Autowired
    private InvestorContactsService investorContactsService;

    @Autowired
    private InvestmentPositioningService investmentPositioningService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取投资人列表
     * @email 1538520381@qq.com
     * @date 2024/5/7 上午10:18
     */
    @GetMapping
    private R getList(Integer page, Integer pageSize, String name, String type, String intentionRegion, String investmentAmount, String investmentType, Long userId) {
        Page<InvestorEntity> investorEntityPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<InvestorEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.equals("null") && !name.isEmpty()) {
            lambdaQueryWrapper.like(InvestorEntity::getName, name);
        }
        if (type != null && !type.equals("null") && !type.isEmpty()) {
            lambdaQueryWrapper.eq(InvestorEntity::getType, type);
        }
        if (investmentAmount != null && !investmentAmount.equals("null") && !investmentAmount.isEmpty()) {
            Set<Long> set = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getValue, investmentAmount)).stream().map(InvestorInvestmentAmountEntity::getInvestorId).collect(Collectors.toSet());
            if (set.isEmpty()) {
                set.add(null);
            }
            lambdaQueryWrapper.in(InvestorEntity::getId, set);
        }
        if (investmentType != null && !investmentType.equals("null") && !investmentType.isEmpty()) {
            Set<Long> set = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getValue, investmentType)).stream().map(InvestorInvestmentTypeEntity::getInvestorId).collect(Collectors.toSet());
            if (set.isEmpty()) {
                set.add(null);
            }
            lambdaQueryWrapper.in(InvestorEntity::getId, set);
        }
        if (intentionRegion != null && !intentionRegion.equals("null") && !intentionRegion.isEmpty()) {
            List<Long> list = investorIntentionRegionService.list(new LambdaQueryWrapper<InvestorIntentionRegionEntity>().eq(InvestorIntentionRegionEntity::getValue, intentionRegion)).stream().map(InvestorIntentionRegionEntity::getInvestorId).collect(Collectors.toList());
            if (list.isEmpty()) {
                list.add(null);
            }
            lambdaQueryWrapper.in(InvestorEntity::getId, list);
        }
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        if (sysUserEntity.getAdmin() == 3) {
            lambdaQueryWrapper.eq(InvestorEntity::getCreateUser, userId);
        } else if (sysUserEntity.getAdmin() == 2) {
            List<Long> ids = sysUserService.list(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getAdmin, 3).eq(SysUserEntity::getCaptain, sysUserEntity.getId())).stream().map((SysUserEntity::getId)).collect(Collectors.toList());
            ids.add(sysUserEntity.getId());
            lambdaQueryWrapper.in(InvestorEntity::getCreateUser, ids);
        }

        investorService.page(investorEntityPage, lambdaQueryWrapper);
        List<InvestorEntity> investorEntityList = investorEntityPage.getRecords();
        List<InvestorDTO> investorDTOList = investorEntityList.stream().map(investorEntity -> {
            InvestorDTO investorDTO = new InvestorDTO();
            BeanUtils.copyProperties(investorEntity, investorDTO);

            List<InvestorInvestmentAmountEntity> investorInvestmentAmountEntities = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntity.getId()));
            List<InvestorInvestmentTypeEntity> investorInvestmentTypeEntities = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntity.getId()));
            List<InvestorAssetOperationModelEntity> investorAssetOperationModelEntities = investorAssetOperationModelService.list(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorEntity.getId()));
            List<InvestorValueAddedServiceEntity> investorValueAddedServiceEntities = investorValueAddedServiceService.list(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorEntity.getId()));
            List<InvestorIntentionRegionEntity> investorIntentionRegionEntities = investorIntentionRegionService.list(new LambdaQueryWrapper<InvestorIntentionRegionEntity>().eq(InvestorIntentionRegionEntity::getInvestorId, investorEntity.getId()));
            List<InvestorContacts> investorContacts = investorContactsService.list(new LambdaQueryWrapper<InvestorContacts>().eq(InvestorContacts::getInvestorId, investorEntity.getId()));
            List<InvestmentPositioning> investmentPositionings = investmentPositioningService.list(new LambdaQueryWrapper<InvestmentPositioning>().eq(InvestmentPositioning::getInvestorId, investorEntity.getId()));
            investorDTO.setInvestorInvestmentAmountEntities(investorInvestmentAmountEntities);
            investorDTO.setInvestorInvestmentTypeEntities(investorInvestmentTypeEntities);
            investorDTO.setInvestorAssetOperationModelEntities(investorAssetOperationModelEntities);
            investorDTO.setInvestorValueAddedServiceEntities(investorValueAddedServiceEntities);
            investorDTO.setInvestorIntentionRegionEntities(investorIntentionRegionEntities);
            investorDTO.setInvestorContacts(investorContacts);
            investorDTO.setInvestmentPositioningEntities(investmentPositionings);
            return investorDTO;
        }).collect(Collectors.toList());
        Page<InvestorDTO> investorDTOPage = new Page<>(page, pageSize);
        BeanUtils.copyProperties(investorEntityPage, investorDTOPage);
        investorDTOPage.setRecords(investorDTOList);
        return R.success("获取成功").put("data", investorDTOPage);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增投资人
     * @email 1538520381@qq.com
     * @date 2024/5/7 下午7:18
     */
    @PostMapping
    private R addInvestor(@RequestBody InvestorDTO investorDTO) {
        InvestorEntity investorEntity = new InvestorEntity();
        BeanUtils.copyProperties(investorDTO, investorEntity);
        investorService.save(investorEntity);
        if (investorDTO.getInvestorInvestmentAmountEntities() != null) {
            for (InvestorInvestmentAmountEntity investorInvestmentAmount : investorDTO.getInvestorInvestmentAmountEntities()) {
                investorInvestmentAmount.setInvestorId(investorEntity.getId());
                investorInvestmentAmountService.save(investorInvestmentAmount);
            }
        }

        if (investorDTO.getInvestorInvestmentTypeEntities() != null) {
            for (InvestorInvestmentTypeEntity investorInvestmentType : investorDTO.getInvestorInvestmentTypeEntities()) {
                investorInvestmentType.setInvestorId(investorEntity.getId());
                investorInvestmentTypeService.save(investorInvestmentType);
            }
        }

        if (investorDTO.getInvestorAssetOperationModelEntities() != null) {
            for (InvestorAssetOperationModelEntity investorAssetOperationModelEntity : investorDTO.getInvestorAssetOperationModelEntities()) {
                investorAssetOperationModelEntity.setInvestorId(investorEntity.getId());
                investorAssetOperationModelService.save(investorAssetOperationModelEntity);
            }
        }

        if (investorDTO.getInvestorValueAddedServiceEntities() != null) {
            for (InvestorValueAddedServiceEntity investorValueAddedService : investorDTO.getInvestorValueAddedServiceEntities()) {
                investorValueAddedService.setInvestorId(investorEntity.getId());
                investorValueAddedServiceService.save(investorValueAddedService);
            }
        }

        if (investorDTO.getInvestorIntentionRegionEntities() != null) {
            for (InvestorIntentionRegionEntity investorIntentionRegion : investorDTO.getInvestorIntentionRegionEntities()) {
                investorIntentionRegion.setInvestorId(investorEntity.getId());
                investorIntentionRegionService.save(investorIntentionRegion);
            }
        }

        if (investorDTO.getInvestorContacts() != null) {
            Set<String> c = new HashSet<>();
            Set<String> p = new HashSet<>();
            for (InvestorContacts investorContact : investorDTO.getInvestorContacts()) {
//                if ((investorContact.getContacts() == null || investorContact.getContacts().isEmpty()) && (investorContact.getPhone() == null || investorContact.getPhone().isEmpty())) {
//                    continue;
//                }
//                if (investorContact.getContacts() == null || investorContact.getContacts().isEmpty()) {
//                    return R.error("新增失败，联系人不能为空");
//                }
//                if (investorContact.getPhone() == null || investorContact.getPhone().isEmpty()) {
//                    return R.error("新增失败，联系方式不能为空");
//                }
//                if (!c.add(investorContact.getContacts())) {
//                    return R.error("新增失败，联系人重复");
//                }
//                if (!p.add(investorContact.getPhone())) {
//                    return R.error("新增失败，联系方式重复");
//                }
                investorContact.setInvestorId(investorEntity.getId());
                investorContactsService.save(investorContact);
            }
        }

        if (investorDTO.getInvestmentPositioningEntities() != null) {
            for (InvestmentPositioning investmentPositioning : investorDTO.getInvestmentPositioningEntities()) {
                investmentPositioning.setInvestorId(investorEntity.getId());
                investmentPositioningService.save(investmentPositioning);
            }
        }

        List<InvestorEntity> list = investorService.list(new LambdaQueryWrapper<InvestorEntity>().eq(InvestorEntity::getName, investorDTO.getName()));
        if (list.size() > 1) {
            return R.success("新增成功,但存在同名投资人,请尽早为其设置具体部门备注").put("type", 1);
        }
        return R.success("新增成功");
    }

    @PutMapping
    private R updateInvestor(@RequestBody InvestorDTO investorDTO) {
        InvestorEntity investorEntity = new InvestorEntity();
        BeanUtils.copyProperties(investorDTO, investorEntity);
        investorService.updateById(investorEntity);

        investorInvestmentAmountService.remove(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorDTO.getId()));
        if (investorDTO.getInvestorInvestmentAmountEntities() != null) {
            for (InvestorInvestmentAmountEntity investorInvestmentAmount : investorDTO.getInvestorInvestmentAmountEntities()) {
                investorInvestmentAmount.setInvestorId(investorEntity.getId());
                investorInvestmentAmountService.save(investorInvestmentAmount);
            }
        }

        investorInvestmentTypeService.remove(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorDTO.getId()));
        if (investorDTO.getInvestorInvestmentTypeEntities() != null) {
            for (InvestorInvestmentTypeEntity investorInvestmentType : investorDTO.getInvestorInvestmentTypeEntities()) {
                investorInvestmentType.setInvestorId(investorEntity.getId());
                investorInvestmentTypeService.save(investorInvestmentType);
            }
        }

        investorAssetOperationModelService.remove(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorDTO.getId()));
        if (investorDTO.getInvestorAssetOperationModelEntities() != null) {
            for (InvestorAssetOperationModelEntity investorAssetOperationModelEntity : investorDTO.getInvestorAssetOperationModelEntities()) {
                investorAssetOperationModelEntity.setInvestorId(investorEntity.getId());
                investorAssetOperationModelService.save(investorAssetOperationModelEntity);
            }
        }

        investorValueAddedServiceService.remove(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorDTO.getId()));
        if (investorDTO.getInvestorValueAddedServiceEntities() != null) {
            for (InvestorValueAddedServiceEntity investorValueAddedService : investorDTO.getInvestorValueAddedServiceEntities()) {
                investorValueAddedService.setInvestorId(investorEntity.getId());
                investorValueAddedServiceService.save(investorValueAddedService);
            }
        }

        investorIntentionRegionService.remove(new LambdaQueryWrapper<InvestorIntentionRegionEntity>().eq(InvestorIntentionRegionEntity::getInvestorId, investorDTO.getId()));
        if (investorDTO.getInvestorIntentionRegionEntities() != null) {
            for (InvestorIntentionRegionEntity investorIntentionRegion : investorDTO.getInvestorIntentionRegionEntities()) {
                investorIntentionRegion.setInvestorId(investorEntity.getId());
                investorIntentionRegionService.save(investorIntentionRegion);
            }
        }

        investmentPositioningService.remove(new LambdaQueryWrapper<InvestmentPositioning>().eq(InvestmentPositioning::getInvestorId, investorDTO.getId()));
        if (investorDTO.getInvestmentPositioningEntities() != null) {
            for (InvestmentPositioning investmentPositioning : investorDTO.getInvestmentPositioningEntities()) {
                investmentPositioning.setInvestorId(investorEntity.getId());
                investmentPositioningService.save(investmentPositioning);
            }
        }

        investorContactsService.remove(new LambdaQueryWrapper<InvestorContacts>().eq(InvestorContacts::getInvestorId, investorDTO.getId()));
        if (investorDTO.getInvestorContacts() != null) {
            Set<String> c = new HashSet<>();
            Set<String> p = new HashSet<>();
            for (InvestorContacts investorContact : investorDTO.getInvestorContacts()) {
//                if ((investorContact.getContacts() == null || investorContact.getContacts().isEmpty()) && (investorContact.getPhone() == null || investorContact.getPhone().isEmpty())) {
//                    continue;
//                }
//                if (investorContact.getContacts() == null || investorContact.getContacts().isEmpty()) {
//                    return R.error("更新失败，联系人不能为空");
//                }
//                if (investorContact.getPhone() == null || investorContact.getPhone().isEmpty()) {
//                    return R.error("更新失败，联系方式不能为空");
//                }
//                if (!c.add(investorContact.getContacts())) {
//                    return R.error("更新失败，联系人重复");
//                }
//                if (!p.add(investorContact.getPhone())) {
//                    return R.error("更新失败，联系方式重复");
//                }
                investorContact.setInvestorId(investorEntity.getId());
                investorContactsService.save(investorContact);
            }
        }

        List<InvestorEntity> list = investorService.list(new LambdaQueryWrapper<InvestorEntity>().eq(InvestorEntity::getName, investorDTO.getName()));
        if (list.size() > 1) {
            return R.success("更新成功,但存在同名投资人,请尽早为其设置具体部门备注").put("type", 1);
        }
        return R.success("更新成功");
    }

    @PostMapping("/addList")
    private R addInvestorList(@RequestBody List<InvestorDTO> investorDTOList) {
        boolean flag = false;
        for (InvestorDTO investorDTO : investorDTOList) {
            if (addInvestor(investorDTO).get("type") != null) {
                flag = true;
            }
        }
        if (flag) {
            return R.success("新增成功,但存在同名投资人,请尽早为其设置具体部门备注").put("type", 1);
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
        List<TraceEntity> list = traceService.list(new LambdaQueryWrapper<TraceEntity>().eq(TraceEntity::getInvestorId, investorId).eq(TraceEntity::getType, 1));
        if (!list.isEmpty()) {
            return R.error("该资产已被匹配且正在被追踪，无法删除");
        }
        investorService.removeById(investorId);
        investorInvestmentTypeService.remove(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorId));
        investorValueAddedServiceService.remove(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorId));
        investorAssetOperationModelService.remove(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorId));
        investorInvestmentAmountService.remove(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorId));
        investorIntentionRegionService.remove(new LambdaQueryWrapper<InvestorIntentionRegionEntity>().eq(InvestorIntentionRegionEntity::getInvestorId, investorId));
        investmentPositioningService.remove(new LambdaQueryWrapper<InvestmentPositioning>().eq(InvestmentPositioning::getInvestorId, investorId));
        traceService.remove(new LambdaQueryWrapper<TraceEntity>().eq(TraceEntity::getInvestorId, investorId));
        return R.success("删除成功");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 投资人匹配
     * @email 1538520381@qq.com
     * @date 2024/5/24 上午11:42
     */
    @GetMapping("/match/{assetId}/{userId}")
    public R match(@PathVariable Long assetId, @PathVariable Long userId) {
        List<TraceDto> traceDtos = traceService.match(assetId, userId);
//        List<InvestorDTO> investorDTOList = investorEntityList.stream().map(investorEntity -> {
//            InvestorDTO investorDTO = new InvestorDTO();
//            BeanUtils.copyProperties(investorEntity, investorDTO);
//
//            List<Long> investorInvestmentAmountId = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentAmountEntity::getInvestmentAmountId).collect(Collectors.toList());
//            List<Long> investorInvestmentTypeId = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorInvestmentTypeEntity::getInvestmentTypeId).collect(Collectors.toList());
//            List<Long> investorAssetOperationModelId = investorAssetOperationModelService.list(new LambdaQueryWrapper<InvestorAssetOperationModelEntity>().eq(InvestorAssetOperationModelEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorAssetOperationModelEntity::getAssetOperationModelId).collect(Collectors.toList());
//            List<Long> investorValueAddedServiceId = investorValueAddedServiceService.list(new LambdaQueryWrapper<InvestorValueAddedServiceEntity>().eq(InvestorValueAddedServiceEntity::getInvestorId, investorEntity.getId())).stream().map(InvestorValueAddedServiceEntity::getValueAddedServiceId).collect(Collectors.toList());
//
//            investorDTO.setInvestmentAmount(investorInvestmentAmountId);
//            investorDTO.setInvestmentType(investorInvestmentTypeId);
//            investorDTO.setAssetOperationModel(investorAssetOperationModelId);
//            investorDTO.setValueAddedService(investorValueAddedServiceId);
//
//            return investorDTO;
//        }).collect(Collectors.toList());
        return R.ok().put("data", traceDtos);
    }

    @GetMapping("/count/{userId}")
    public R count(@PathVariable Long userId) {
        LocalDate currentDate = LocalDate.now();
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            YearMonth yearMonth = YearMonth.from(currentDate.minusMonths(i));
            LocalDate firstDayOfMonth = yearMonth.atDay(1);
            LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
            LocalDateTime startOfMonth = LocalDateTime.of(firstDayOfMonth, LocalTime.MIN);
            LocalDateTime endOfMonth = LocalDateTime.of(lastDayOfMonth, LocalTime.MAX);
            xData.add(yearMonth.getMonthValue() + "月");

            LambdaQueryWrapper<InvestorEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            SysUserEntity sysUserEntity = sysUserService.getById(userId);
            if (sysUserEntity.getAdmin() == 3) {
                lambdaQueryWrapper.eq(InvestorEntity::getCreateUser, userId);
            } else if (sysUserEntity.getAdmin() == 2) {
                List<Long> ids = sysUserService.list(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getAdmin, 3).eq(SysUserEntity::getCaptain, sysUserEntity.getId())).stream().map((SysUserEntity::getId)).collect(Collectors.toList());
                ids.add(sysUserEntity.getId());
                lambdaQueryWrapper.in(InvestorEntity::getCreateUser, ids);
            }

            yData.add(investorService.count(lambdaQueryWrapper.between(InvestorEntity::getCreateTime, startOfMonth, endOfMonth)));
        }
        return R.ok().put("xData", xData).put("yData", yData);
    }

    @GetMapping("/fun")
    public R fun() {
        List<InvestorEntity> investorEntities = investorService.list();
        for (InvestorEntity investorEntity : investorEntities) {
            String[] contacts = investorEntity.getContact().split("、");
            for (String contact : contacts) {
                InvestorContacts investorContacts = new InvestorContacts();
                investorContacts.setInvestorId(investorEntity.getId());
                investorContacts.setContacts(" ");
                investorContacts.setPhone(contact);
                investorContactsService.save(investorContacts);
            }
        }
        return R.ok();
    }
}
