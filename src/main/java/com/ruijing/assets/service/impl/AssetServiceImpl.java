package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.constant.ruiJingConstant.*;
import com.ruijing.assets.dao.AssetDao;
import com.ruijing.assets.entity.dto.AssetInsertDTO;
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
import org.springframework.stereotype.Service;
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
    private CustomerService customerService;

    @Autowired
    private AssetHighlightService assetHighlightService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private MinioUtil minioUtil;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<AssetEntity> assetEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //获得需要查询的资产名字
        if (params.get("key") != null) {
            String key = params.get("key").toString();
            if (!StringUtils.isEmpty(key)) {
                assetEntityLambdaQueryWrapper.like(AssetEntity::getAssetName, key);
            }
        }
        IPage<AssetEntity> page = this.page(
                new Query<AssetEntity>().getPage(params),
                assetEntityLambdaQueryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    public R getAssetInfo(Long assetId) {
        AssetEntity assetEntity = this.getById(assetId);
        AssetVoInDetail assetVoInDetail = new AssetVoInDetail();

        //0,查询债权亮点
        List<AssetHighlight> assetHighlights = assetHighlightService.list(new LambdaQueryWrapper<AssetHighlight>()
                .eq(AssetHighlight::getAssetId, assetId));
        if (!CollectionUtils.isEmpty(assetHighlights)) {
            List<Highlight> highlights = new ArrayList<>();
            assetHighlights.stream().forEach(
                    assetHighlight ->
                    {
                        Highlight highlight = Highlight
                                .builder()
                                .highlightTitle(assetHighlight.getHighlightTitle())
                                .highlightContent(assetHighlight.getHighlightContent())
                                .build();
                        highlights.add(highlight);
                    }
            );
            assetVoInDetail.setHighlights(highlights);
        }

        //1，封装基本属性
        BeanUtils.copyProperties(assetEntity, assetVoInDetail);


        //3,设置浏览量和收藏量
        AssetCollectionBrowseEntity assetCollectionBrowseEntity = assetCollectionBrowseService.getOne(new LambdaQueryWrapper<AssetCollectionBrowseEntity>()
                .eq(AssetCollectionBrowseEntity::getAssetId, assetEntity.getId()));
        assetVoInDetail.setBrowse(assetCollectionBrowseEntity.getBrowse());
        assetVoInDetail.setCollection(assetCollectionBrowseEntity.getCollection());
        //4,将数字转化为对应的代表
        assetVoInDetail.setLitigationStatusString(LitigationStatusConstant.litigationStatusMap.get(assetVoInDetail.getLitigationStatus()));
        assetVoInDetail.setProvinceString(ProvinceAndCityConstant.provinceAndCityMap.get(assetVoInDetail.getProvince()));
        assetVoInDetail.setDisposalMethodString(DisposalMethodTypeConstant.disposalMethodType.get(assetEntity.getDisposalMethod()));

        //5,资产所有的图片
        List<AssetImageEntity> images = assetImageService
                .list(new LambdaQueryWrapper<AssetImageEntity>().eq(AssetImageEntity::getAssetId, assetId))
                .stream()
                .map(assetImageEntity ->
                        {
                            //处理他们的图片
                            assetImageEntity.setImage(minioUtil.getEndpoint() + "/" + assetImageEntity.getImage());
                            return assetImageEntity;
                        }
                )
                .collect(Collectors.toList());


        //6,获得资产抵押物
        List<AssetCollateralEntity> assetCollateralEntities = assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>()
                        .eq(AssetCollateralEntity::getAssetId, assetId))
                .stream()
                .peek(assetCollateralEntity -> assetCollateralEntity.setAssetTypeString(AssetCollateralTypeConstant.assetCollateralTypeMap.get(assetCollateralEntity.getCollateralType())))
                .collect(Collectors.toList());

        System.out.println(assetCollateralEntities);


        //7,担保人
        List<AssetGuaranteeEntity> assetGuaranteeEntities = assetGuaranteeService.list(new LambdaQueryWrapper<AssetGuaranteeEntity>().eq(AssetGuaranteeEntity::getAssetId,
                        assetId))
                .stream()
                .peek(assetGuaranteeEntity -> {
                    assetGuaranteeEntity.setMethodString(GuaranteeGuaranteeMethodConstant.guaranteeGuaranteeMethodMap.get(assetGuaranteeEntity.getMethod()));
                    if (assetGuaranteeEntity.getGuaranteeName().length() <= 4) {
                        //7.1,对名字进行加密
                        this.nameEncryption(assetGuaranteeEntity);
                    }
                })
                .collect(Collectors.toList());


        return R.ok()
                .put("images", images)
                .put("guarantees", assetGuaranteeEntities)
                .put("assetVoInDetail", assetVoInDetail)
                .put("collateral", assetCollateralEntities);
    }

    @Override
    public void addAsset(AssetInsertDTO assetInsertDTO) {
        AssetEntity assetEntity = new AssetEntity();
        BeanUtils.copyProperties(assetInsertDTO, assetEntity);
        //设置默认状态 默认状态为 1，拟处置
        assetEntity.setDisposalStatus(DisposalStatus.PROPOSED_DISPOSAL.getCode());
        //设置上架状态
        assetEntity.setOnShelfStatus(OnShelfStatus.TO_BE_PUT_ON_THE_SHELF.getCode());
        //插入
        this.save(assetEntity);


        //插入亮点
        List<Highlight> highlights = assetInsertDTO.getHighlights();
        if (!CollectionUtils.isEmpty(highlights)) {
            //待插入的亮点
            List<AssetHighlight> highlightEntitiesToBeSaved = new ArrayList<>();
            highlights.forEach(highlight -> {
                AssetHighlight assetHighlight = AssetHighlight
                        .builder()
                        .assetId(assetEntity.getId())
                        .highlightTitle(highlight.getHighlightTitle())
                        .highlightContent(highlight.getHighlightContent())
                        .build();
                highlightEntitiesToBeSaved.add(assetHighlight);
            });
            //批量插入
            assetHighlightService.saveBatch(highlightEntitiesToBeSaved);
        }

        //插入担保人
        if (!CollectionUtils.isEmpty(assetInsertDTO.getGuarantees())) {
            //待插入的抵押人
            List<AssetGuaranteeEntity> assetGuaranteeEntitiesToBeSaved = new ArrayList<>();
            assetInsertDTO.getGuarantees().forEach(guaranteesDTO -> {
                AssetGuaranteeEntity assetGuaranteeEntity = new AssetGuaranteeEntity();
                assetGuaranteeEntity.setAssetId(assetEntity.getId());
                assetGuaranteeEntity.setGuaranteeName(guaranteesDTO.getGuaranteeName());
                assetGuaranteeEntity.setMethod(guaranteesDTO.getMethod());
                assetGuaranteeEntitiesToBeSaved.add(assetGuaranteeEntity);
            });
            //批量插入
            assetGuaranteeService.saveBatch(assetGuaranteeEntitiesToBeSaved);
        }

        //插入抵押物
        if (!CollectionUtils.isEmpty(assetInsertDTO.getCollateral())) {
            //待插入的抵押人
            List<AssetCollateralEntity> assetCollateralEntitiesToBeSaved = new ArrayList<>();
            assetInsertDTO.getCollateral().forEach(collateralDTO -> {
                AssetCollateralEntity assetGuaranteeEntity = new AssetCollateralEntity();
                assetGuaranteeEntity.setAssetName(collateralDTO.getAssetName());
                assetGuaranteeEntity.setArea(collateralDTO.getArea());
                assetGuaranteeEntity.setLocation(collateralDTO.getLocation());
                assetGuaranteeEntity.setDescription(collateralDTO.getDescription());
                assetGuaranteeEntity.setCollateralType(collateralDTO.getCollateralType());
                assetGuaranteeEntity.setAssetId(assetEntity.getId());
                assetCollateralEntitiesToBeSaved.add(assetGuaranteeEntity);
            });
            //批量插入
            assetCollateralService.saveBatch(assetCollateralEntitiesToBeSaved);
        }
    }

    @Override
    public String upload(byte[] bytes, String originalFilename, String contentType, Long assetId) {
        try {
            String originalUrl = uploadService.uploadFile(bytes, originalFilename, contentType);
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
        uploadService.deleteFileByObjectName(objectName);
        //删除数据库中的
        assetImageService.removeById(assetImageId);
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

}
