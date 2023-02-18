package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetCustomerBrowseDao;
import com.ruijing.assets.entity.pojo.AssetCustomerBrowseEntity;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import com.ruijing.assets.entity.vo.assetCustomerBrowseVO.AssetCustomerBrowseVO;
import com.ruijing.assets.entity.vo.assetCustomerBrowseVO.BrowseCustomerVO;
import com.ruijing.assets.entity.vo.assetCustomerCollectionVO.AssetCustomerCollectionVO;
import com.ruijing.assets.entity.vo.assetCustomerCollectionVO.CollectionCustomerVO;
import com.ruijing.assets.service.AssetCustomerBrowseService;
import com.ruijing.assets.service.AssetService;
import com.ruijing.assets.service.CustomerService;
import com.ruijing.assets.util.unUsing.Query;
import com.ruijing.assets.util.using.IpageConvertUtil;
import com.ruijing.assets.util.using.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service("assetCustomerBrowseService")
public class AssetCustomerBrowseServiceImpl extends ServiceImpl<AssetCustomerBrowseDao, AssetCustomerBrowseEntity> implements AssetCustomerBrowseService {

    @Autowired
    CustomerService customerService;
    @Lazy
    @Autowired
    AssetService assetService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AssetCustomerBrowseEntity> page = this.page(
                new Query<AssetCustomerBrowseEntity>().getPage(params),
                new LambdaQueryWrapper<>()
        );
        List<AssetCustomerBrowseEntity> assetCustomerBrowseEntities = page.getRecords();
        List<AssetCustomerBrowseVO> assetCustomerBrowseVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(assetCustomerBrowseEntities)) {
            return null;
        } else {
            //map的格式
            //记录资产id 和 其对应的浏览的人的id集合
            //{key : assetId  value : 所有收藏该债权的浏览的人的id集合}
            Map<Long, List<Long>> assetIdAndBrowseCustomerIdsMap = new HashMap<>();
            //记录收藏人id和其对应的浏览时间
            Map<Long, Date> customerIdAndBrowseTimeMap = new HashMap<>();
            assetCustomerBrowseEntities.forEach(assetCustomerCollectionEntity -> {
                Long key = assetCustomerCollectionEntity.getAssetId();
                Long customerId = assetCustomerCollectionEntity.getCustomerId();
                //记录记录收藏人id和其对应的收藏时间
                customerIdAndBrowseTimeMap.put(customerId, assetCustomerCollectionEntity.getBrowseTime());
                if (assetIdAndBrowseCustomerIdsMap.containsKey(key)) {
                    //包含
                    List<Long> customerIds = assetIdAndBrowseCustomerIdsMap.get(key);
                    customerIds.add(customerId);
                } else {
                    //不包含
                    ArrayList<Long> customerIds = new ArrayList<>();
                    customerIds.add(customerId);
                    assetIdAndBrowseCustomerIdsMap.put(key, customerIds);
                }
            });
            //获得需要返回的数量
            //创建返回结果数组
            Set<Map.Entry<Long, List<Long>>> assetIdAndCustomerIdsSet = assetIdAndBrowseCustomerIdsMap.entrySet();
            assetIdAndCustomerIdsSet.forEach(assetIdAndCustomerId -> {
                Long assetId = assetIdAndCustomerId.getKey();
                //查询资产信息
                AssetEntity assetEntity = assetService.getById(assetId);
                AssetCustomerBrowseVO assetCustomerBrowseVO = new AssetCustomerBrowseVO();
                assetCustomerBrowseVO.setAssetEntity(assetEntity);
                //设置收藏量
                //收藏量就是 value所对应的集合的大小
                assetCustomerBrowseVO.setBrowseNum(Integer.toUnsignedLong(assetIdAndCustomerId.getValue().size()));
                //设置收藏人集合
                List<BrowseCustomerVO> browseCustomerVOS = new ArrayList<>();
                //遍历收藏人id  封装
                if (!CollectionUtils.isEmpty(assetIdAndCustomerId.getValue())) {
                    assetIdAndCustomerId.getValue().forEach(customerId -> {
                        BrowseCustomerVO browseCustomerVO = new BrowseCustomerVO();
                        //查询用户信息
                        CustomerEntity customerEntity = customerService.getById(customerId);
                        BeanUtils.copyProperties(customerEntity, browseCustomerVO);
                        //设置收藏时间
                        browseCustomerVO.setBrowseTime(customerIdAndBrowseTimeMap.get(customerEntity.getId()));
                        browseCustomerVOS.add(browseCustomerVO);
                    });
                }
                assetCustomerBrowseVO.setCustomerEntityList(browseCustomerVOS);
                assetCustomerBrowseVOS.add(assetCustomerBrowseVO);
            });
        }
        //转化
        IPage<AssetCustomerBrowseVO> resultPage = IpageConvertUtil.iPageS2T(page, assetCustomerBrowseVOS);
        return new PageUtils(resultPage);
    }


}
