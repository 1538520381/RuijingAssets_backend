package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetCustomerCollectionDao;
import com.ruijing.assets.entity.vo.assetCustomerCollectionVO.AssetCustomerCollectionVO;
import com.ruijing.assets.entity.pojo.AssetCustomerCollectionEntity;

import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import com.ruijing.assets.entity.vo.assetCustomerCollectionVO.CollectionCustomerVO;
import com.ruijing.assets.service.AssetCustomerCollectionService;
import com.ruijing.assets.service.AssetService;
import com.ruijing.assets.service.CustomerService;
import com.ruijing.assets.util.using.IpageConvertUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service("assetCustomerCollectionService")
public class AssetCustomerCollectionServiceImpl extends ServiceImpl<AssetCustomerCollectionDao, AssetCustomerCollectionEntity> implements AssetCustomerCollectionService {

    @Autowired
    CustomerService customerService;
    @Lazy
    @Autowired
    AssetService assetService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<AssetCustomerCollectionEntity> assetCustomerCollectionEntityLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        IPage<AssetCustomerCollectionEntity> page = this.page(
                new Query<AssetCustomerCollectionEntity>().getPage(params),
                assetCustomerCollectionEntityLambdaQueryWrapper
        );
        List<AssetCustomerCollectionEntity> assetCustomerCollectionEntities = page.getRecords();
        List<AssetCustomerCollectionVO> assetCustomerCollectionVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(assetCustomerCollectionEntities)) {
            return null;
        } else {
            //map的格式
            //记录资产id 和 其对应的收藏人id集合
            //{key : assetId  value : 所有收藏该债权的收藏人id集合}
            Map<Long, List<Long>> assetIdAndCustomerIdsMap = new HashMap<>();
            //记录收藏人id和其对应的收藏时间
            Map<Long, Date> customerIdAndCollectionTimeMap = new HashMap<>();
            assetCustomerCollectionEntities.forEach(assetCustomerCollectionEntity -> {
                Long key = assetCustomerCollectionEntity.getAssetId();
                Long customerId = assetCustomerCollectionEntity.getCustomerId();
                //记录记录收藏人id和其对应的收藏时间
                customerIdAndCollectionTimeMap.put(customerId, assetCustomerCollectionEntity.getCollectionTime());
                if (assetIdAndCustomerIdsMap.containsKey(key)) {
                    //包含
                    List<Long> customerIds = assetIdAndCustomerIdsMap.get(key);
                    customerIds.add(customerId);
                } else {
                    //不包含
                    ArrayList<Long> customerIds = new ArrayList<>();
                    customerIds.add(customerId);
                    assetIdAndCustomerIdsMap.put(key, customerIds);
                }
            });
            //获得需要返回的数量
            //创建返回结果数组
            Set<Map.Entry<Long, List<Long>>> assetIdAndCustomerIdsSet = assetIdAndCustomerIdsMap.entrySet();
            assetIdAndCustomerIdsSet.forEach(assetIdAndCustomerId -> {
                Long assetId = assetIdAndCustomerId.getKey();
                //查询资产信息
                AssetEntity assetEntity = assetService.getById(assetId);
                AssetCustomerCollectionVO assetCustomerCollectionVO = new AssetCustomerCollectionVO();
                assetCustomerCollectionVO.setAssetEntity(assetEntity);
                //设置收藏量
                //收藏量就是 value所对应的集合的大小
                assetCustomerCollectionVO.setCollectionNum(Integer.toUnsignedLong(assetIdAndCustomerId.getValue().size()));
                //设置收藏人集合
                List<CollectionCustomerVO> collectionCustomerVOS = new ArrayList<>();
                //遍历收藏人id  封装
                if (!CollectionUtils.isEmpty(assetIdAndCustomerId.getValue())) {
                    assetIdAndCustomerId.getValue().forEach(customerId -> {
                        CollectionCustomerVO customerVO = new CollectionCustomerVO();
                        //查询用户信息
                        CustomerEntity customerEntity = customerService.getById(customerId);
                        BeanUtils.copyProperties(customerEntity, customerVO);
                        //设置收藏时间
                        customerVO.setCollectionTime(customerIdAndCollectionTimeMap.get(customerEntity.getId()));
                        collectionCustomerVOS.add(customerVO);
                    });
                }
                assetCustomerCollectionVO.setCustomerEntityList(collectionCustomerVOS);
                assetCustomerCollectionVOS.add(assetCustomerCollectionVO);
            });
        }
        //转化
        IPage<AssetCustomerCollectionVO> resultPage = IpageConvertUtil.iPageS2T(page, assetCustomerCollectionVOS);
        return new PageUtils(resultPage);
    }


}
