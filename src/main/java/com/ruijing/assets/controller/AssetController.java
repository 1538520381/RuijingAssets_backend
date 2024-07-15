package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.annotation.SysLog;
import com.ruijing.assets.entity.dto.*;
import com.ruijing.assets.entity.pojo.*;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.enume.status.OnShelfStatus;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.*;

import com.ruijing.assets.service.impl.InvestorServiceImpl;
import com.ruijing.assets.util.using.PageUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 房屋资产基本信息表
 * @email 3161788646@qq.com
 * @date 2022/12/15 0:55
 */

@RestController
@RequestMapping("assets/asset")
@Slf4j
public class AssetController {
    @Autowired
    private AssetService assetService;
    @Autowired
    private TraceService traceService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private InvestorService investorService;
    @Autowired
    private InvestorIntentionRegionService investorIntentionRegionService;
    @Autowired
    private InvestorInvestmentTypeService investorInvestmentTypeService;
    @Autowired
    private InvestorInvestmentAmountService investorInvestmentAmountService;


    /*
     * @author: K0n9D1KuA
     * @description: 修改债权信息
     * @param: assetUpdateDTO 债权基本信息 + 担保人 + 抵押物 + 亮点 修改
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 22:19
     */
    @PostMapping("/update")
    @SysLog(operationName = "修改债权", operationType = 2)
    public R update(@RequestBody AssetUpdateDTO assetUpdateDTO) {
        assetService.updateAsset(assetUpdateDTO);
        return R.ok();
    }


    /*
     * @author: K0n9D1KuA
     * @description: 修改债权基本信息
     * @param: assetEntity
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 22:23
     */
    @PostMapping("/updateStatus")
    @SysLog(operationName = "修改债权状态", operationType = 2)
    public R updateStatus(@RequestBody AssetEntity assetEntity) {
        assetService.updateById(assetEntity);
        return R.ok();
    }


    /*
     * @author: K0n9D1KuA
     * @description: 改变资产排序顺序
     * @param: assetEntity
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 22:23
     */
    @PostMapping("/changePosition")
    @SysLog(operationName = "改变资产排序顺序", operationType = 2)
    public R changePosition(@RequestBody AssetEntity assetEntity) {
        assetService.updateById(assetEntity);
        return R.ok();
    }


    /*
     * @author: K0n9D1KuA
     * @description: 债权上架
     * @param: onShelfDTO 债权id + 下架时间
     * @return:
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 22:24
     */

    @PostMapping("/onShelf")
    public R onShelf(@RequestBody OnShelfDTO onShelfDTO) {
        AssetEntity assetEntity = new AssetEntity();
        BeanUtils.copyProperties(onShelfDTO, assetEntity);
        //设置开始时间
        assetEntity.setStartTime(new Date());
        //设置状态  已上架
        assetEntity.setOnShelfStatus(OnShelfStatus.ON_THE_SHELF.getCode());
        //数据库更改
        assetService.updateById(assetEntity);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 查询资产列表
     * @param: params  key 支持按照资产名字模糊查询 + 分页参数
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:36
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = assetService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/list/{userId}")
    public R listByUserId(@PathVariable Long userId) {
        return R.ok().put("data", assetService.listByUserId(userId));
    }


    /*
     * @author: K0n9D1KuA
     * @description: 根据资产id获取资产所有信息
     * @param: assetId 资产id
     * @return: com.ruijing.assets.entity.result.R 结果
     * @date: 2022/12/16 15:07
     */
    @GetMapping("/assetInfo/{assetId}")
    public R getAssetInfo(@PathVariable Long assetId) {
        return assetService.getAssetInfo(assetId);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 添加债权信息
     * @param: assetAddDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 19:21
     */
    @PostMapping("/addAsset")
    @SysLog(operationName = "新增债权", operationType = 2)
    public R addAsset(@RequestBody AssetAddDTO assetAddDTO) {
        assetService.addAsset(assetAddDTO);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 上传资产图片
     * @param: assetId 资产id
     * @param: file
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 17:18
     */
    @PostMapping("upload/{assetId}")
    public R upload(@PathVariable Long assetId, @RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] bytes = file.getBytes();
            assetService.upload(bytes, originalFilename, contentType, assetId);
            return R.ok();
        } catch (Exception e) {
            //上传文件失败
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }
    }


    /*
     * @author: K0n9D1KuA
     * @description: 删除资产图片
     * @param: assetImageId 要删除的资产图片id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 17:18
     */
    @GetMapping("delete/{assetImageId}")
    @SysLog(operationName = "删除资产图片", operationType = 2)
    public R upload(@PathVariable Long assetImageId) {
        try {
            assetService.deleteImage(assetImageId);
            return R.ok();
        } catch (Exception e) {
            //删除文件失败
            //抛出异常 删除文件失败
            throw new RuiJingException(
                    RuiJingExceptionEnum.DELETE_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.DELETE_FILE_FAILED.getCode()
            );
        }
    }


    /*
     * @author: K0n9D1KuA
     * @description:
     * @param: 删除资产
     * @param: assetId 资产id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 19:43
     */
    @GetMapping("/deleteAsset/{assetId}")
    public R deleteAsset(@PathVariable Long assetId) {
        List<TraceEntity> list = traceService.list(new LambdaQueryWrapper<TraceEntity>().eq(TraceEntity::getAssetId, assetId));
        if (!list.isEmpty()) {
            return R.error("该资产已被匹配，无法删除");
        }
        assetService.deleteAsset(assetId);
        return R.ok();
    }

    @GetMapping("/matchAll")
    public R matchAll(@RequestParam Long userId) {
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        List<InvestorEntity> list;
        if (!sysUserEntity.getAdmin()) {
            list = investorService.list(new LambdaQueryWrapper<InvestorEntity>().eq(InvestorEntity::getCreateUser, userId));
        } else {
            list = investorService.list();
        }
        for (InvestorEntity investorEntity : list) {
            List<InvestorIntentionRegionEntity> list1 = investorIntentionRegionService.list(new LambdaQueryWrapper<InvestorIntentionRegionEntity>().eq(InvestorIntentionRegionEntity::getInvestorId, investorEntity.getId()));
            List<InvestorInvestmentTypeEntity> list2 = investorInvestmentTypeService.list(new LambdaQueryWrapper<InvestorInvestmentTypeEntity>().eq(InvestorInvestmentTypeEntity::getInvestorId, investorEntity.getId()));
            List<InvestorInvestmentAmountEntity> list3 = investorInvestmentAmountService.list(new LambdaQueryWrapper<InvestorInvestmentAmountEntity>().eq(InvestorInvestmentAmountEntity::getInvestorId, investorEntity.getId()));
            StringBuilder str1 = new StringBuilder(" ");
            for (InvestorIntentionRegionEntity investorIntentionRegion : list1) {
                str1.append(investorIntentionRegion.getValue()).append(",");
            }
            StringBuilder str2 = new StringBuilder(" ");
            for (InvestorInvestmentTypeEntity investorInvestmentType : list2) {
                str2.append(investorInvestmentType.getInvestmentTypeId()).append(",");
            }
            StringBuilder str3 = new StringBuilder(" ");
            for (InvestorInvestmentAmountEntity investorInvestmentAmount : list3) {
                str3.append(investorInvestmentAmount.getInvestmentAmountId()).append(",");
            }
            match(investorEntity.getId(), str1.toString(), str2.toString(), str3.toString(), userId);
        }
        return R.ok();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 债权匹配
     * @email 1538520381@qq.com
     * @date 2024/5/23 下午9:21
     */
    @GetMapping("/match")
    public R match(@RequestParam Long investorId, @RequestParam String intentionRegion, @RequestParam String investmentType, @RequestParam String investmentAmount, @RequestParam Long userId) {
        List<Integer> list1 = new ArrayList<>();
        String[] split = investmentType.substring(1, investmentType.length() - 1).split(",");
        for (String s : split) {
            list1.add(Integer.parseInt(s));
        }
        List<Integer> list2 = new ArrayList<>();
        split = investmentAmount.substring(1, investmentAmount.length() - 1).split(",");
        for (String s : split) {
            list2.add(Integer.parseInt(s));
        }
        List<AssetDto> match = assetService.match(Arrays.asList(intentionRegion.substring(1, intentionRegion.length() - 1).split(",")), list1, list2);
        List<TraceDto> list = new ArrayList<>();
        for (AssetDto assetDto : match) {
            TraceEntity trace = new TraceEntity();
            trace.setInvestorId(investorId);
            trace.setAssetId(assetDto.getId());
            trace.setType(0);
            trace.setUserId(userId);
            Long l = traceService.addByUnique(trace);
            TraceDto traceDto = new TraceDto();
            traceDto.setId(l);
            traceDto.setInvestorId(investorId);
            traceDto.setInvestorName(investorService.getById(investorId).getName());
            traceDto.setAssetId(assetDto.getId());
            traceDto.setAssetName(assetDto.getAssetName());
            traceDto.setAssetUserName(assetDto.getName());
            SysUserEntity sysUserEntity1 = sysUserService.getById(assetDto.getCreateUser());
            traceDto.setCreateAssetName(sysUserEntity1.getName());
            SysUserEntity sysUserEntity2 = sysUserService.getById(investorService.getById(investorId).getCreateUser());
            traceDto.setCreateInvestorName(sysUserEntity2.getName());
            list.add(traceDto);

        }
        return R.ok().put("data", list);
    }

    @GetMapping("/count")
    public R count() {
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
            yData.add(assetService.count(new LambdaQueryWrapper<AssetEntity>().between(AssetEntity::getCreateTime, startOfMonth, endOfMonth)));
        }
        return R.ok().put("xData", xData).put("yData", yData);
    }
}
