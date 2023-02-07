package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.AssetImageEntity;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 房屋资产图片
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
public interface AssetImageService extends IService<AssetImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

