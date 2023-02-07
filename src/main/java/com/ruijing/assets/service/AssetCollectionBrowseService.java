package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.AssetCollectionBrowseEntity;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 每个资产浏览量与收藏量
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
public interface AssetCollectionBrowseService extends IService<AssetCollectionBrowseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

