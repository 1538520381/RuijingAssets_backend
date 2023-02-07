package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetCustomerCollectionDao;
import com.ruijing.assets.entity.vo.customerCollectionVO.AssetCustomerCollectionVO;
import com.ruijing.assets.entity.pojo.AssetCustomerCollectionEntity;

import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import com.ruijing.assets.entity.queryDTO.AssetCustomerCollectionQueryDTO;
import com.ruijing.assets.service.AssetCustomerCollectionService;
import com.ruijing.assets.service.AssetService;
import com.ruijing.assets.service.CustomerService;
import com.ruijing.assets.util.using.IpageConvertUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("assetCustomerCollectionService")
public class AssetCustomerCollectionServiceImpl extends ServiceImpl<AssetCustomerCollectionDao, AssetCustomerCollectionEntity> implements AssetCustomerCollectionService {

    @Autowired
    CustomerService customerService;
    @Lazy
    @Autowired
    AssetService assetService;


    @Override
    public PageUtils queryPage(Map<String, Object> params, AssetCustomerCollectionQueryDTO assetCustomerCollectionQueryDTO) {
        //根据资产名字模糊查询
        LambdaQueryWrapper<AssetCustomerCollectionEntity> assetCustomerCollectionEntityLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        //时间降序 or 升序
        assetCustomerCollectionEntityLambdaQueryWrapper.orderBy(
                true,
                assetCustomerCollectionQueryDTO.getTimeOrder().equals(1),
                AssetCustomerCollectionEntity::getCollectionTime);
        IPage<AssetCustomerCollectionEntity> page = this.page(
                new Query<AssetCustomerCollectionEntity>().getPage(params),
                assetCustomerCollectionEntityLambdaQueryWrapper
        );
        List<AssetCustomerCollectionEntity> assetCustomerCollectionEntities = page.getRecords();
        List<AssetCustomerCollectionVO> assetCustomerCollectionVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(assetCustomerCollectionEntities)) {
            return null;
        } else {
            assetCustomerCollectionEntities.forEach(assetCustomerCollectionEntity ->
            {
                //查询用户信息
                CustomerEntity customerEntity = customerService.getById(assetCustomerCollectionEntity.getCustomerId());
                //查询资产信息
                AssetEntity assetEntity = assetService.getById(assetCustomerCollectionEntity.getAssetId());
                //收藏时间
                Date collectionTime = assetCustomerCollectionEntity.getCollectionTime();
                AssetCustomerCollectionVO assetCustomerCollectionVO = AssetCustomerCollectionVO.builder()
                        .assetEntity(assetEntity)
                        .customerEntity(customerEntity)
                        .collectionTime(collectionTime)
                        .build();
                assetCustomerCollectionVOS.add(assetCustomerCollectionVO);
            });
        }
        //转化
        IPage<AssetCustomerCollectionVO> resultPage = IpageConvertUtil.iPageS2T(page, assetCustomerCollectionVOS);
        return new PageUtils(resultPage);
    }





}
