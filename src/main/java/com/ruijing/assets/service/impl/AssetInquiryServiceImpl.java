package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetInqueryDao;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.AssetInquiryEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import com.ruijing.assets.entity.vo.assetInquiryVO.AssetInquiryVO;
import com.ruijing.assets.entity.vo.assetInquiryVO.InquiryCustomerVO;
import com.ruijing.assets.entity.vo.assetVO.AssetVoInHomePage;
import com.ruijing.assets.service.*;

import com.ruijing.assets.util.using.IpageConvertUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


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
    public PageUtils queryPage(Map<String, Object> params) {



        IPage<AssetInquiryEntity> source = this.page(
                new Query<AssetInquiryEntity>().getPage(params),
                new LambdaQueryWrapper<>()
        );

        List<AssetInquiryEntity> assetInquiryEntities = source.getRecords();
        List<AssetInquiryVO> targetRecords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(assetInquiryEntities)) {
            //map的格式
            //记录资产id 和 其对应的收藏人id集合
            //{key : assetId  value : 所有咨询该债权的人的id集合}
            Map<Long, List<Long>> assetIdAndInquiryIdsMap = new HashMap<>();
            //记录收藏人id和其对应的咨询时间
            Map<Long, Date> inquiryIdAndInquiryTimeMap = new HashMap<>();
            assetInquiryEntities.forEach(assetInquiryEntity -> {
                Long key = assetInquiryEntity.getAssetId();
                Long customerId = assetInquiryEntity.getInquiryCustomerId();
                //记录记录收藏人id和其对应的收藏时间
                inquiryIdAndInquiryTimeMap.put(customerId, assetInquiryEntity.getInquiryTime());
                if (assetIdAndInquiryIdsMap.containsKey(key)) {
                    //包含
                    List<Long> inquiryIds = assetIdAndInquiryIdsMap.get(key);
                    inquiryIds.add(customerId);
                } else {
                    //不包含
                    ArrayList<Long> inquiryIds = new ArrayList<>();
                    inquiryIds.add(customerId);
                    assetIdAndInquiryIdsMap.put(key, inquiryIds);
                }
            });
            Set<Map.Entry<Long, List<Long>>> assetIdAndInquiryIdsSet = assetIdAndInquiryIdsMap.entrySet();
            assetIdAndInquiryIdsSet.forEach(assetIdAndInquiryIds -> {
                //资产id
                Long assetId = assetIdAndInquiryIds.getKey();
                List<Long> inquiryCustomerIds = assetIdAndInquiryIds.getValue();
                AssetInquiryVO assetInquiryVO = new AssetInquiryVO();
                //查询资产信息
                AssetEntity assetEntity = assetService.getById(assetId);
                assetInquiryVO.setAssetEntity(assetEntity);
                //设置询价的数量
                int inquiryNum = this.count(new LambdaQueryWrapper<AssetInquiryEntity>()
                        .eq(AssetInquiryEntity::getAssetId, assetEntity.getId())
                );
                //设置询价的数量
                assetInquiryVO.setInquiryNum(Integer.toUnsignedLong(inquiryNum));
                //设置咨询人
                List<InquiryCustomerVO> inquiryCustomerVOList = new ArrayList<>();
                inquiryCustomerIds.forEach(inquiryCustomerId -> {
                    AssetInquiryEntity currentAssetInquiryEntity = assetInquiryEntities
                            .stream()
                            .filter(assetInquiryEntity -> assetInquiryEntity.getInquiryCustomerId() == inquiryCustomerId)
                            .filter(assetInquiryEntity -> assetInquiryEntity.getAssetId() == assetId)
                            .collect(Collectors.toList())
                            .get(0);
                    InquiryCustomerVO inquiryCustomerVO = new InquiryCustomerVO();
                    BeanUtils.copyProperties(currentAssetInquiryEntity, inquiryCustomerVO);
                    inquiryCustomerVOList.add(inquiryCustomerVO);
                });
                assetInquiryVO.setInquiryCustomerVOList(inquiryCustomerVOList);
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
