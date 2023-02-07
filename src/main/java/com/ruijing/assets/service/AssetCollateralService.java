package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.AssetCollateralEntity;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 房屋债权抵押物
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
public interface AssetCollateralService extends IService<AssetCollateralEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

