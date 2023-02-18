package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.AssetCustomerBrowseEntity;
import com.ruijing.assets.entity.pojo.AssetCustomerCollectionEntity;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;


public interface AssetCustomerBrowseService extends IService<AssetCustomerBrowseEntity> {

    PageUtils queryPage(Map<String, Object> params);


}

