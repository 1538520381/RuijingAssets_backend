package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.entity.pojo.AssetCollateralEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.AssetCollateralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description AssetCollateral Controller
 * @email 1538520381@qq.com
 * @date 2024/06/13 19:36
 */
@RestController
@RequestMapping("/assets/assetCollateral")
public class AssetCollateralController {
    @Autowired
    private AssetCollateralService assetCollateralService;

    @RequestMapping("/{assetId}")
    private R getByAssetId(@PathVariable Long assetId) {
        return R.ok().put("data", assetCollateralService.list(new LambdaQueryWrapper<AssetCollateralEntity>().eq(AssetCollateralEntity::getAssetId, assetId)));
    }
}
