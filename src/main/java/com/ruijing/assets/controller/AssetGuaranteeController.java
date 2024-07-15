package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.entity.pojo.AssetGuaranteeEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.AssetGuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description AssetGuarantee Controller
 * @email 1538520381@qq.com
 * @date 2024/06/13 21:21
 */
@RestController
@RequestMapping("/assets/assetGuarantee")
public class AssetGuaranteeController {
    @Autowired
    private AssetGuaranteeService assetGuaranteeService;

    @RequestMapping("/{assetId}")
    private R getByAssetId(@PathVariable Long assetId) {
        return R.ok().put("data", assetGuaranteeService.list(new LambdaQueryWrapper<AssetGuaranteeEntity>().eq(AssetGuaranteeEntity::getAssetId, assetId)));
    }
}
