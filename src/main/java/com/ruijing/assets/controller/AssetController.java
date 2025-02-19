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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;


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
    private AssetFileService assetFileService;


    @Value("${asset.file.path}")
    private String basePath;


    /*
     * @author: K0n9D1KuA
     * @description: 修改债权信息
     * @param: assetUpdateDTO 债权基本信息 + 担保人 + 抵押物 + 亮点 修改
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 22:19
     */
    @PostMapping("/update")
    @SysLog(operationName = "修改债权", operationType = 2)
    public R update(@RequestBody AssetDto assetDto) {
        assetService.updateAsset(assetDto);
        return R.ok();
    }

    @PutMapping("unshelf/{assetId}")
    public R unshelf(@PathVariable Long assetId) {
        try {
            AssetEntity assetEntity = assetService.getById(assetId);
            assetEntity.setOnShelfStatus(1);
            assetService.updateById(assetEntity);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
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
//        assetEntity.setStartTime(new Date());
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
    @DeleteMapping("/deleteAsset/{assetId}")
    public R deleteAsset(@PathVariable Long assetId) {
        List<TraceEntity> list = traceService.list(new LambdaQueryWrapper<TraceEntity>().eq(TraceEntity::getAssetId, assetId).eq(TraceEntity::getType, 1));
        if (!list.isEmpty()) {
            return R.error("该资产已被匹配且正在被追踪，无法删除");
        }
        assetService.deleteAsset(assetId);
        traceService.remove(new LambdaQueryWrapper<TraceEntity>().eq(TraceEntity::getAssetId, assetId));
        return R.ok();
    }

    @Autowired
    private InvestorIntentionRegionService investorIntentionRegionService;

    @Autowired
    private InvestorInvestmentTypeService investorInvestmentTypeService;
    @Autowired
    private InvestorInvestmentAmountService investorInvestmentAmountService;

    @GetMapping("/matchAll")
    public R matchAll(@RequestParam Long userId) {
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        List<InvestorEntity> list;
        if (sysUserEntity.getAdmin() == 3) {
            list = investorService.list(new LambdaQueryWrapper<InvestorEntity>().eq(InvestorEntity::getCreateUser, userId));
        } else if (sysUserEntity.getAdmin() == 2) {
            List<Long> ids = sysUserService.list(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getAdmin, 3).eq(SysUserEntity::getCaptain, sysUserEntity.getId())).stream().map((SysUserEntity::getId)).collect(Collectors.toList());
            ids.add(sysUserEntity.getId());
            list = investorService.list(new LambdaQueryWrapper<InvestorEntity>().in(InvestorEntity::getCreateUser, ids));
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
                str2.append(investorInvestmentType.getValue()).append(",");
            }
            StringBuilder str3 = new StringBuilder(" ");
            for (InvestorInvestmentAmountEntity investorInvestmentAmount : list3) {
                str3.append(investorInvestmentAmount.getValue()).append(",");
            }
            Map<String, String> map = new HashMap<>();
            map.put("investorId", investorEntity.getId().toString());
            map.put("intentionRegion", str1.toString());
            map.put("investmentType", str2.toString());
            map.put("investmentAmount", str3.toString());
            map.put("userId", userId.toString());
            match(map);
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
    @PostMapping("/match")
    public R match(@RequestBody Map<String, String> map) {
        String investorId = map.get("investorId");
        String intentionRegion = map.get("intentionRegion");
        String investmentType = map.get("investmentType");
        String investmentAmount = map.get("investmentAmount");
        String userId = map.get("userId");
        List<String> list1 = new ArrayList<>();
        if (investmentType != null && !investmentType.isEmpty() && !investmentType.equals(" ")) {
            String[] split = investmentType.substring(1, investmentType.length() - 1).split(",");
            if (!split[0].isEmpty()) {
                list1 = new ArrayList<>(Arrays.asList(split));

            }
        }
        List<String> list2 = new ArrayList<>();
        if (investmentAmount != null && !investmentAmount.isEmpty() && !investmentAmount.equals(" ")) {
            String[] split = investmentAmount.substring(1, investmentAmount.length() - 1).split(",");
            if (!split[0].isEmpty()) {
                list2 = new ArrayList<>(Arrays.asList(split));
            }
        }
        List<String> list3 = new ArrayList<>();
        if (intentionRegion != null && !intentionRegion.isEmpty() && !intentionRegion.equals(" ")) {
            String[] split = intentionRegion.substring(1, intentionRegion.length() - 1).split(",");
            if (!split[0].isEmpty()) {
                list3 = new ArrayList<>(Arrays.asList(split));
            }
        }
        List<AssetDto> match = assetService.match(list3, list1, list2);
        List<TraceDto> list = new ArrayList<>();
        for (AssetDto assetDto : match) {
            TraceEntity trace = new TraceEntity();
            trace.setInvestorId(Long.parseLong(investorId));
            trace.setAssetId(assetDto.getId());
            trace.setType(0);
            trace.setUserId(Long.parseLong(userId));
            Long l = traceService.addByUnique(trace);
            TraceDto traceDto = new TraceDto();
            traceDto.setId(l);
            traceDto.setInvestorId(Long.parseLong(investorId));
            traceDto.setInvestorName(investorService.getById(Long.parseLong(investorId)).getName());
            traceDto.setAssetId(assetDto.getId());
//            traceDto.setAssetName(assetDto.getAssetInformation());
            traceDto.setAssetUserName(assetDto.getName());
            traceDto.setCreateAssetName(assetDto.getOperator());
            traceDto.setCreateInvestorName(investorService.getById(Long.parseLong(investorId)).getOperator());
            list.add(traceDto);

        }
        return R.ok().put("data", list);
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

            LambdaQueryWrapper<AssetEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            SysUserEntity sysUserEntity = sysUserService.getById(userId);
            if (sysUserEntity.getAdmin() == 3) {
                lambdaQueryWrapper.eq(AssetEntity::getCreateUser, userId);
            } else if (sysUserEntity.getAdmin() == 2) {
                List<Long> ids = sysUserService.list(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getAdmin, 3).eq(SysUserEntity::getCaptain, sysUserEntity.getId())).stream().map((SysUserEntity::getId)).collect(Collectors.toList());
                ids.add(sysUserEntity.getId());
                lambdaQueryWrapper.in(AssetEntity::getCreateUser, ids);
            }

            yData.add(assetService.count(lambdaQueryWrapper.between(AssetEntity::getCreateTime, startOfMonth, endOfMonth)));
        }
        return R.ok().put("xData", xData).put("yData", yData);
    }

    @PostMapping("/upload/file/{assetId}")
    public R upload(@RequestParam("file") MultipartFile file, @PathVariable Long assetId) {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("111:{}", basePath + fileName);
        AssetFile assetFile = new AssetFile();
        assetFile.setName(fileName);
        assetFile.setAssetId(assetId);
        assetFile.setOriginalName(originalFilename);
        log.info("222:{}", originalFilename);
        boolean save = assetFileService.save(assetFile);
        log.info("333:{}", save);
        return R.ok("上传成功" + basePath + fileName);
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
    public R addAsset(@RequestBody AssetDto assetDTO) {
        assetService.addAsset(assetDTO);
        return R.ok();
    }

    @GetMapping("/asset/{assetId}")
    public R getAssetById(@PathVariable Long assetId) {
        AssetDto assetDto = assetService.getDtoById(assetId);
        return R.ok().put("asset", assetDto);
    }

    @GetMapping("/download/{name}")
    public void download(@PathVariable String name, HttpServletResponse response) {
        log.info("1:");
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            log.info("2:");
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            log.info("3:");
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("4:");
    }

    @DeleteMapping("/assetFile/{id}")
    public R removeAssetFile(@PathVariable Long id) {
        assetFileService.removeById(id);
        return R.ok();
    }
}
