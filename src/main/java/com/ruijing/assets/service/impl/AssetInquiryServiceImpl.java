package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetInqueryDao;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.AssetInquiryEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import com.ruijing.assets.entity.queryDTO.AssetInquiryDTO;
import com.ruijing.assets.entity.vo.assetInquiryVO.AssetInquiryVO;
import com.ruijing.assets.entity.vo.assetVO.AssetVoInHomePage;
import com.ruijing.assets.service.*;

import com.ruijing.assets.util.using.IpageConvertUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("assetInquiryService")
public class AssetInquiryServiceImpl extends ServiceImpl<AssetInqueryDao, AssetInquiryEntity> implements AssetInquiryService {


    @Autowired
    CustomerService customerService;

    @Autowired
    AssetService assetService;

    @Autowired
    AssetImageService assetImageService;

    @Autowired
    AssetCollectionBrowseService assetCollectionBrowseService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, AssetInquiryDTO assetInquiryDTO) {
        //时间升序or降序
        LambdaQueryWrapper<AssetInquiryEntity> assetInquiryEntityLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        assetInquiryEntityLambdaQueryWrapper
                .orderBy(
                        true,
                        assetInquiryDTO.getTimeOrder().equals(1),
                        AssetInquiryEntity::getInquiryTime);

        IPage<AssetInquiryEntity> source = this.page(
                new Query<AssetInquiryEntity>().getPage(params),
                assetInquiryEntityLambdaQueryWrapper
        );

        List<AssetInquiryEntity> assetInquiryEntities = source.getRecords();
        List<AssetInquiryVO> targetRecords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(assetInquiryEntities)) {
            assetInquiryEntities.forEach(assetInquiryEntity ->
            {
                AssetInquiryVO assetInquiryVO = new AssetInquiryVO();
                BeanUtils.copyProperties(assetInquiryEntity, assetInquiryVO);
                Long assetId = assetInquiryVO.getAssetId();
                Long inquiryCustomerId = assetInquiryVO.getInquiryCustomerId();
                //查询客户信息
                CustomerEntity customerEntity = customerService.getById(inquiryCustomerId);
                //查询资产信息
                AssetEntity assetEntity = assetService.getById(assetId);
                assetInquiryVO.setAssetEntity(assetEntity);
                assetInquiryVO.setCustomerEntity(customerEntity);
                targetRecords.add(assetInquiryVO);
            });
        }
        IPage<AssetInquiryVO> target = IpageConvertUtil.iPageS2T(source, targetRecords);
        return new PageUtils(target);
    }

    @Override
    public void inquiry(AssetInquiryEntity assetInquiryEntity) {

    }

    @Override
    public List<AssetVoInHomePage> inquiryInfo() {
        return null;
    }


}
