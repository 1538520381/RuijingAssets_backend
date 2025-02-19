package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.constant.ruiJingConstant.*;
import com.ruijing.assets.dao.AssetDao;
import com.ruijing.assets.entity.dto.AssetAddDTO;
import com.ruijing.assets.entity.dto.AssetDto;
import com.ruijing.assets.entity.dto.AssetUpdateDTO;
import com.ruijing.assets.entity.pojo.*;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.entity.vo.assetVO.AssetVoInDetail;
import com.ruijing.assets.entity.vo.assetVO.Highlight;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.enume.status.DisposalStatus;
import com.ruijing.assets.enume.status.OnShelfStatus;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.*;
import com.ruijing.assets.util.using.MinioUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service("assetService")
public class AssetServiceImpl extends ServiceImpl<AssetDao, AssetEntity> implements AssetService {

    @Autowired
    private AssetCollectionBrowseService assetCollectionBrowseService;

    @Autowired
    private AssetImageService assetImageService;

    @Autowired
    private AssetCollateralService assetCollateralService;

    @Autowired
    private AssetGuaranteeService assetGuaranteeService;

    @Autowired
    private AssetCustomerCollectionService assetCustomerCollectionService;

    @Autowired
    private AssetHighlightService assetHighlightService;

    @Autowired
    private AssetCustomerBrowseService assetCustomerBrowseService;


    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private AssetFileService assetFileService;

    //循环依赖 延迟加载
    @Lazy
    @Autowired
    private AssetInquiryService assetInquiryService;
    @Autowired
    private SysUserServiceImpl sysUserService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<AssetEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(AssetEntity::getCreateTime);
        String userId = params.get("userId").toString();
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        if (sysUserEntity.getAdmin() == 3) {
            lambdaQueryWrapper.eq(AssetEntity::getCreateUser, userId);
        } else if (sysUserEntity.getAdmin() == 2) {
            List<Long> ids = sysUserService.list(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getAdmin, 3).eq(SysUserEntity::getCaptain, sysUserEntity.getId())).stream().map((SysUserEntity::getId)).collect(Collectors.toList());
            ids.add(sysUserEntity.getId());
            lambdaQueryWrapper.in(AssetEntity::getCreateUser, ids);
        }

        Page<AssetEntity> page = new Page<>(Long.parseLong(params.get("current").toString()), Long.parseLong(params.get("size").toString()));

        if (params.get("name") != null && !params.get("name").toString().isEmpty()) {
            lambdaQueryWrapper.like(AssetEntity::getName, params.get("name").toString());
        }
        if (params.get("creditRightBank") != null && !params.get("creditRightBank").toString().isEmpty()) {
            lambdaQueryWrapper.like(AssetEntity::getCreditRightBank, params.get("creditRightBank").toString());
        }
        if (params.get("guarantee") != null && !params.get("guarantee").toString().isEmpty()) {
            lambdaQueryWrapper.like(AssetEntity::getGuarantee, params.get("guarantee").toString());
        }


        List<AssetEntity> allAssets = list(lambdaQueryWrapper);
//        if (params.get("location") != null && !params.get("location").toString().isEmpty()) {
//            for (int i = 0; i < allAssets.size(); i++) {
//                List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, allAssets.get(i).getId()));
//                boolean flag = false;
//                for (AssetCollateralEntity assetCollateralEntity : assetCollateralEntities) {
//                    if (assetCollateralEntity.getLocation() != null && assetCollateralEntity.getLocation().equals(params.get("location").toString())) {
//                        flag = true;
//                        break;
//                    }
//                }
//                if (!flag) {
//                    allAssets.remove(i);
//                    i--;
//                }
//            }
//        }

        for (int i = 0; i < allAssets.size(); i++) {
            List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, allAssets.get(i).getId()));
            boolean flag1 = !(params.get("location") != null && !params.get("location").toString().isEmpty());
            boolean flag2 = !(params.get("collateralName") != null && !params.get("collateralName").toString().isEmpty());
            boolean flag3 = !(params.get("collateralType") != null && !params.get("collateralType").toString().isEmpty());
            for (AssetCollateralEntity assetCollateralEntity : assetCollateralEntities) {
                if (params.get("location") != null && !params.get("location").toString().isEmpty() && !flag1) {
                    if (assetCollateralEntity.getLocation() != null && assetCollateralEntity.getLocation().equals(params.get("location").toString())) {
                        flag1 = true;
                    }
                }
                if (params.get("collateralName") != null && !params.get("collateralName").toString().isEmpty() && !flag2) {
                    if (assetCollateralEntity.getAssetName() != null && assetCollateralEntity.getAssetName().contains(params.get("collateralName").toString())) {
                        flag2 = true;
                    }
                }
                if (params.get("collateralType") != null && !params.get("collateralType").toString().isEmpty() && !flag3) {
                    if (assetCollateralEntity.getCollateralType() != null && assetCollateralEntity.getCollateralType().equals(params.get("collateralType").toString())) {
                        flag3 = true;
                    }
                }
            }
            if (!flag1 || !flag2 || !flag3) {
                allAssets.remove(i);
                i--;
            }
        }
        int current = Integer.parseInt(params.get("current").toString());
        int size = Integer.parseInt(params.get("size").toString());
        Page<AssetDto> page1 = new Page<>(Long.parseLong(params.get("current").toString()), Long.parseLong(params.get("size").toString()));
        page1.setTotal(allAssets.size());
        allAssets = allAssets.subList(Math.min((current - 1) * size, allAssets.size()), Math.min(current * size, allAssets.size()));
//        this.page(page, lambdaQueryWrapper);
//        List<AssetEntity> assetEntities = page.getRecords();
        List<AssetDto> assetDtos = allAssets.stream().map(assetEntity -> {
            AssetDto assetDto = new AssetDto();
            BeanUtils.copyProperties(assetEntity, assetDto);
            List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, assetEntity.getId()));
            List<AssetFile> assetFiles = assetFileService.list(new LambdaQueryWrapper<AssetFile>().eq(AssetFile::getAssetId, assetEntity.getId()));
            assetDto.setAssetCollateralEntities(assetCollateralEntities);
            assetDto.setAssetFiles(assetFiles);
            return assetDto;
        }).collect(Collectors.toList());

        page1.setRecords(assetDtos);
        return new PageUtils(page1);
    }

    @Override
    public List<AssetDto> listByUserId(Long userId) {
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        List<AssetEntity> assetEntities;
        if (sysUserEntity.getAdmin() == 1) {
            assetEntities = list();
        } else if (sysUserEntity.getAdmin() == 2) {
            List<Long> ids = sysUserService.list(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getAdmin, 3).eq(SysUserEntity::getCaptain, sysUserEntity.getId())).stream().map((SysUserEntity::getId)).collect(Collectors.toList());
            ids.add(sysUserEntity.getId());
            LambdaQueryWrapper<AssetEntity> assetEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            assetEntityLambdaQueryWrapper.in(AssetEntity::getCreateUser, ids);
            assetEntities = list(assetEntityLambdaQueryWrapper);
        } else {
            LambdaQueryWrapper<AssetEntity> assetEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            assetEntityLambdaQueryWrapper.eq(AssetEntity::getCreateUser, userId);
            assetEntities = list(assetEntityLambdaQueryWrapper);
        }
        List<AssetDto> assetDtos = new ArrayList<>();
        for (AssetEntity assetEntity : assetEntities) {
            AssetDto assetDto = new AssetDto();
            BeanUtils.copyProperties(assetEntity, assetDto);
            List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(
                    new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, assetEntity.getId())
            );
//            List<AssetGuaranteeEntity> assetGuaranteeEntities = assetGuaranteeService.list(new LambdaQueryWrapper<AssetGuaranteeEntity>().eq(AssetGuaranteeEntity::getAssetId, assetEntity.getId()));
//            List<AssetHighlight> assetHighlights = assetHighlightService.list(new LambdaQueryWrapper<AssetHighlight>().eq(AssetHighlight::getAssetId, assetEntity.getId()));
            List<AssetFile> assetFiles = assetFileService.list(new LambdaQueryWrapper<AssetFile>().eq(AssetFile::getAssetId, assetEntity.getId()));
            assetDto.setAssetCollateralEntities(assetCollateralEntities);
//            assetDto.setAssetGuaranteeEntities(assetGuaranteeEntities);
//            assetDto.setAssetHighlights(assetHighlights);
            assetDto.setAssetFiles(assetFiles);
            assetDtos.add(assetDto);
        }
        return assetDtos;
    }


    @Override
    public R getAssetInfo(Long assetId) {
        AssetEntity assetEntity = this.getById(assetId);
        AssetVoInDetail assetVoInDetail = new AssetVoInDetail();

        //1, 查询债权亮点
        List<AssetHighlight> assetHighlights = assetHighlightService.list(new LambdaQueryWrapper<AssetHighlight>()
                .eq(AssetHighlight::getAssetId, assetId));
        if (!CollectionUtils.isEmpty(assetHighlights)) {
            List<Highlight> highlights = new ArrayList<>();
            assetHighlights.stream().forEach(
                    assetHighlight ->
                    {
                        Highlight highlight = Highlight
                                .builder()
//                                .highlightTitle(assetHighlight.getHighlightTitle())
                                .highlightContent(assetHighlight.getHighlightContent())
                                .build();
                        highlights.add(highlight);
                    }
            );
            assetVoInDetail.setHighlights(highlights);
        }

        //2，封装基本属性
        BeanUtils.copyProperties(assetEntity, assetVoInDetail);


        //3,设置浏览量和收藏量
        AssetCollectionBrowseEntity assetCollectionBrowseEntity = assetCollectionBrowseService.getOne(new LambdaQueryWrapper<AssetCollectionBrowseEntity>()
                .eq(AssetCollectionBrowseEntity::getAssetId, assetEntity.getId()));
        if (!Objects.isNull(assetCollectionBrowseEntity)) {
            assetVoInDetail.setBrowse(assetCollectionBrowseEntity.getBrowse());
            assetVoInDetail.setCollection(assetCollectionBrowseEntity.getCollection());
        }

        //4,将数字转化为对应的代表
        assetVoInDetail.setLitigationStatusString(LitigationStatusConstant.litigationStatusMap.get(assetVoInDetail.getLitigationStatus()));
        assetVoInDetail.setProvinceString(ProvinceAndCityConstant.provinceAndCityMap.get(assetVoInDetail.getProvince()));
        assetVoInDetail.setDisposalMethodString(DisposalMethodTypeConstant.disposalMethodType.get(assetEntity.getDisposalMethod()));


        //5,资产所有的图片
        List<AssetImageEntity> images = assetImageService
                .list(new LambdaQueryWrapper<AssetImageEntity>().eq(AssetImageEntity::getAssetId, assetId));
        if (!CollectionUtils.isEmpty(images)) {
            images = images.stream()
                    .peek(assetImageEntity ->
                            {
                                // 5, 处理他们的图片
                                // ruijing/2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg 变为
                                // http://175.178.189.129:9000/ruijing/2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg
                                String imageUrl = MinioUtil.processImage(assetImageEntity.getImage());
                                //处理他们的图片
                                assetImageEntity.setImage(imageUrl);
                            }
                    )
                    .collect(Collectors.toList());
        }


        //6,获得资产抵押物
        List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>()
                .eq(AssetCollateralEntity::getAssetId, assetId));
        if (!CollectionUtils.isEmpty(assetCollateralEntities)) {
            assetCollateralEntities = assetCollateralEntities.stream()
                    //6.1,将抵押物的种类转化为具体的String
                    .peek(assetCollateralEntity -> assetCollateralEntity.setCollateralTypeString(AssetCollateralTypeConstant.assetCollateralTypeMap.get(assetCollateralEntity.getCollateralType())))
                    .collect(Collectors.toList());
        }


        //7,担保人
        List<AssetGuaranteeEntity> assetGuaranteeEntities = assetGuaranteeService.list(new LambdaQueryWrapper<AssetGuaranteeEntity>().eq(AssetGuaranteeEntity::getAssetId,
                assetId));


//        if (!CollectionUtils.isEmpty(assetGuaranteeEntities)) {
//            assetGuaranteeEntities = assetGuaranteeEntities.stream()
//                    .peek(assetGuaranteeEntity -> {
//                        assetGuaranteeEntity.setMethodString(GuaranteeGuaranteeMethodConstant.guaranteeGuaranteeMethodMap.get(assetGuaranteeEntity.getMethod()));
//                        if (assetGuaranteeEntity.getGuaranteeName().length() <= 4) {
//                            //7.1,对名字进行加密
//                            this.nameEncryption(assetGuaranteeEntity);
//                        }
//                    })
//                    .collect(Collectors.toList());
//        }


        return R.ok()
                .put("images", images)
                .put("guarantees", assetGuaranteeEntities)
                .put("assetVoInDetail", assetVoInDetail)
                .put("collateral", assetCollateralEntities);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 添加债权信息
     * @param: assetAddDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 19:21
     */
    @Transactional
    @Override
    public void addAsset(AssetDto assetDto) {
        AssetEntity assetEntity = new AssetEntity();
        BeanUtils.copyProperties(assetDto, assetEntity);
        assetEntity.setOnShelfStatus(0);
        save(assetEntity);
        for (AssetCollateralEntity assetCollateralEntity : assetDto.getAssetCollateralEntities()) {
            assetCollateralEntity.setAssetId(assetEntity.getId());
            assetCollateralService.save(assetCollateralEntity);
        }
//        for (AssetGuaranteeEntity assetGuaranteeEntity : assetDto.getAssetGuaranteeEntities()) {
//            assetGuaranteeEntity.setAssetId(assetEntity.getId());
//            assetGuaranteeService.save(assetGuaranteeEntity);
//        }
//        for (AssetHighlight assetHighlight : assetDto.getAssetHighlights()) {
//            assetHighlight.setAssetId(assetEntity.getId());
//            assetHighlightService.save(assetHighlight);
//        }
    }

    @Override
    public String upload(byte[] bytes, String originalFilename, String contentType, Long assetId) {
        try {
            String originalUrl = minioUtil.uploadFile(bytes, originalFilename, contentType);
            //将图片存入数据库中
            AssetImageEntity assetImageEntity = new AssetImageEntity();
            assetImageEntity.setImage(originalUrl);
            assetImageEntity.setAssetId(assetId);
            assetImageService.save(assetImageEntity);
            //返回最后的url
            return minioUtil.END_POINT + "/" + originalFilename;
        } catch (Exception e) {
            //上传文件失败
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }
    }

    @Override
    public void deleteImage(Long assetImageId) {
        //查出照片实体
        AssetImageEntity assetImageEntity = assetImageService.getById(assetImageId);
        //ruijing/2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg
        String originalImage = assetImageEntity.getImage();
        //变为2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg
        String objectName = convertImageUrlToObjectName(originalImage);
        //调用删除方法
        minioUtil.deleteFileByObjectName(objectName);
        //删除数据库中的
        assetImageService.removeById(assetImageId);
    }

    /*
     * @author: K0n9D1KuA
     * @description:
     * @param: 删除资产
     * @param: assetId 资产id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 19:43
     */
    @Transactional
    @Override
    public void deleteAsset(Long assetId) {

        //删除资产实体
        this.removeById(assetId);

        //删除该资产的亮点
        assetHighlightService.remove(new LambdaQueryWrapper<AssetHighlight>()
                .eq(AssetHighlight::getAssetId, assetId)
        );

        //删除资产抵押物
        assetCollateralService.remove(new LambdaQueryWrapper<AssetCollateralEntity>()
                .eq(AssetCollateralEntity::getAssetId, assetId)
        );

        //模拟异常 测试事务
//        int i = 1 / 0;

        //删除资产担保人
        assetGuaranteeService.remove(new LambdaQueryWrapper<AssetGuaranteeEntity>()
                .eq(AssetGuaranteeEntity::getAssetId, assetId));


        //删除资产的浏览量 和 收藏量
        assetCollectionBrowseService.remove(new LambdaQueryWrapper<AssetCollectionBrowseEntity>()
                .eq(AssetCollectionBrowseEntity::getAssetId, assetId));

        assetFileService.remove(new LambdaQueryWrapper<AssetFile>().eq(AssetFile::getAssetId, assetId));


        //删除客户对该债权的收藏关系
        assetCustomerCollectionService.remove(new LambdaQueryWrapper<AssetCustomerCollectionEntity>()
                .eq(AssetCustomerCollectionEntity::getAssetId, assetId));

        //删除客户对该债权的浏览信息
        assetCustomerBrowseService.remove(new LambdaQueryWrapper<AssetCustomerBrowseEntity>()
                .eq(AssetCustomerBrowseEntity::getAssetId, assetId));


        //删除客户对该债权的询价绑定关系
        assetInquiryService.remove(new LambdaQueryWrapper<AssetInquiryEntity>()
                .eq(AssetInquiryEntity::getAssetId, assetId));


    }


    /*
     * @author: K0n9D1KuA
     * @description: 修改债权信息
     * @param: assetUpdateDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 22:19
     */
    @Override
    @Transactional
    public void updateAsset(AssetDto assetDto) {

        AssetEntity assetEntity = new AssetEntity();
        //修改债权基本信息
        BeanUtils.copyProperties(assetDto, assetEntity);
        this.updateById(assetEntity);

        Long assetId = assetEntity.getId();

        //删除该资产的亮点
        assetHighlightService.remove(new LambdaQueryWrapper<AssetHighlight>()
                .eq(AssetHighlight::getAssetId, assetId)
        );

        //删除资产抵押物
        assetCollateralService.remove(new LambdaQueryWrapper<AssetCollateralEntity>()
                .eq(AssetCollateralEntity::getAssetId, assetId)
        );


        //删除资产担保人
        assetGuaranteeService.remove(new LambdaQueryWrapper<AssetGuaranteeEntity>()
                .eq(AssetGuaranteeEntity::getAssetId, assetId));

        assetFileService.remove(new LambdaQueryWrapper<AssetFile>().eq(AssetFile::getAssetId, assetId));

        for (AssetCollateralEntity assetCollateralEntity : assetDto.getAssetCollateralEntities()) {
            assetCollateralEntity.setAssetId(assetEntity.getId());
            assetCollateralService.save(assetCollateralEntity);
        }

//        for (AssetHighlight assetHighlight : assetDto.getAssetHighlights()) {
//            assetHighlight.setAssetId(assetEntity.getId());
//            assetHighlightService.save(assetHighlight);
//        }
//
//        for (AssetGuaranteeEntity assetGuaranteeEntity : assetDto.getAssetGuaranteeEntities()) {
//            assetGuaranteeEntity.setAssetId(assetEntity.getId());
//            assetGuaranteeService.save(assetGuaranteeEntity);
//        }

        for (AssetFile assetFile : assetDto.getAssetFiles()) {
            assetFile.setAssetId(assetEntity.getId());
            assetFileService.save(assetFile);
        }
    }


    /*
     * @author: K0n9D1KuA
     * @description: 名字加密处理
     * @param: assetGuaranteeEntity 担保人
     * @return: null
     * @date: 2022/12/24 14:34
     */
    private void nameEncryption(AssetGuaranteeEntity assetGuaranteeEntity) {
        //名字处理
        //如果三个字 邱远海 --> 邱*海
        //如果两个字 邱远  --> 邱*
        //如果四个字 邱远海海 --> 邱**海
        int length = assetGuaranteeEntity.getGuaranteeName().length();
        String realName = null;
        if (length == 2) {
            realName = assetGuaranteeEntity.getGuaranteeName().charAt(0) + "*";
        } else {
            realName = assetGuaranteeEntity.getGuaranteeName().charAt(0) + "*" + assetGuaranteeEntity.getGuaranteeName().substring(length - 1);
        }
        assetGuaranteeEntity.setGuaranteeName(realName);
    }

    //  根据url获得objectName
    //  ruijing/2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
    //  2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
    private String convertImageUrlToObjectName(String originalUrl) {
        //  ruijing/
        String cutSubString = minioUtil.getBucket() + "/";
        return originalUrl.substring(cutSubString.length());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 债权匹配
     * @email 1538520381@qq.com
     * @date 2024/5/23 下午9:09
     */
    public List<AssetDto> match(List<String> intentionRegion, List<String> investmentType, List<String> investmentAmount) {
        LambdaQueryWrapper<AssetEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!intentionRegion.isEmpty()) {
            lambdaQueryWrapper.in(AssetEntity::getRegion, intentionRegion);
        }
        if (!investmentAmount.isEmpty()) {
            lambdaQueryWrapper.and(wq -> {
                boolean flag = false;
                if (investmentAmount.contains("1000万以下")) {
                    wq.between(AssetEntity::getCreditRightFare, 0, 1000);
                    flag = true;
                }
                if (investmentAmount.contains("1000万-3000万")) {
                    if (flag) {
                        wq.or();
                    }
                    wq.between(AssetEntity::getCreditRightFare, 1000, 3000);
                    flag = true;
                }
                if (investmentAmount.contains("3000万-6000万")) {
                    if (flag) {
                        wq.or();
                    }
                    wq.between(AssetEntity::getCreditRightFare, 3000, 6000);
                }
                if (investmentAmount.contains("6000万-1亿")) {
                    if (flag) {
                        wq.or();
                    }
                    wq.between(AssetEntity::getCreditRightFare, 6000, 10000);
                }
                if (investmentAmount.contains("1亿-3亿")) {
                    if (flag) {
                        wq.or();
                    }
                    wq.between(AssetEntity::getCreditRightFare, 10000, 30000);
                }
                if (investmentAmount.contains("3亿以上")) {
                    if (flag) {
                        wq.or();
                    }
                    wq.gt(AssetEntity::getCreditRightFare, 30000);
                }
            });
        }
//        if (!investmentType.isEmpty()) {
//            lambdaQueryWrapper.in(AssetEntity::getCollateralType, investmentType);
//        }
        List<AssetEntity> list = list(lambdaQueryWrapper);
        for (int i = 0; i < list.size(); i++) {
            boolean flag = false;
            List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, list.get(i).getId()));
            for (AssetCollateralEntity assetCollateralEntity : assetCollateralEntities) {
                if (investmentType.contains(assetCollateralEntity.getCollateralType())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list.remove(i);
                i--;
            }
//            if (collateralType == null) {
//                list.remove(i);
//                i--;
//                continue;
//            }
//            String[] split = collateralType.split(";");
//            boolean flag = false;
//            for (String s : split) {
//                if (investmentType.contains(s)) {
//                    flag = true;
//                    break;
//                }
//            }
//            if (!flag) {
//                list.remove(i);
//                i--;
//            }
        }
//        for (int i = 0; i < list.size(); i++) {
//            List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, list.get(i).getId()));
//            boolean flag = false;
//            for (AssetCollateralEntity assetCollateralEntity : assetCollateralEntities) {
//                if (investmentType.contains(assetCollateralEntity.getCollateralType())) {
//                    flag = true;
//                    break;
//                }
//            }
//            if (!flag) {
//                list.remove(list.get(i));
//                i--;
//            }
//        }
        List<AssetDto> list1 = new ArrayList<>();
        for (AssetEntity assetEntity : list) {
            AssetDto assetDto = new AssetDto();
            BeanUtils.copyProperties(assetEntity, assetDto);
            List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, assetEntity.getId()));
//            List<AssetGuaranteeEntity> assetGuaranteeEntities = assetGuaranteeService.list(new LambdaQueryWrapper<AssetGuaranteeEntity>().eq(AssetGuaranteeEntity::getAssetId, assetEntity.getId()));
//            List<AssetHighlight> assetHighlights = assetHighlightService.list(new LambdaQueryWrapper<AssetHighlight>().eq(AssetHighlight::getAssetId, assetEntity.getId()));
            assetDto.setAssetCollateralEntities(assetCollateralEntities);
//            assetDto.setAssetGuaranteeEntities(assetGuaranteeEntities);
//            assetDto.setAssetHighlights(assetHighlights);
            list1.add(assetDto);
        }
        return list1;
    }

    @Override
    public AssetDto getDtoById(Long id) {
        AssetEntity assetEntity = getById(id);
        List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, id));
//        List<AssetGuaranteeEntity> assetGuaranteeEntities = assetGuaranteeService.list(new LambdaQueryWrapper<AssetGuaranteeEntity>().eq(AssetGuaranteeEntity::getAssetId, id));
//        List<AssetHighlight> assetHighlights = assetHighlightService.list(new LambdaQueryWrapper<AssetHighlight>().eq(AssetHighlight::getAssetId, id));
        List<AssetFile> assetFiles = assetFileService.list(new LambdaQueryWrapper<AssetFile>().eq(AssetFile::getAssetId, id));
        AssetDto assetDto = new AssetDto();
        BeanUtils.copyProperties(assetEntity, assetDto);
        assetDto.setAssetCollateralEntities(assetCollateralEntities);
//        assetDto.setAssetGuaranteeEntities(assetGuaranteeEntities);
//        assetDto.setAssetHighlights(assetHighlights);
        assetDto.setAssetFiles(assetFiles);
        return assetDto;
    }
}
