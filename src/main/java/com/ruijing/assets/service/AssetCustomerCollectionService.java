package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.AssetCustomerCollectionEntity;
import com.ruijing.assets.entity.queryDTO.AssetCustomerCollectionQueryDTO;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 客户收藏资产表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-27 00:20:54
 */
public interface AssetCustomerCollectionService extends IService<AssetCustomerCollectionEntity> {

    PageUtils queryPage(Map<String, Object> params, AssetCustomerCollectionQueryDTO assetCustomerCollectionQueryDTO);


}

