package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.AssetGuaranteeEntity;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 房屋债权保证人
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
public interface AssetGuaranteeService extends IService<AssetGuaranteeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

