package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.entity.pojo.AssetHighlight;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.AssetHighlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description AssetHighlight Controller
 * @email 1538520381@qq.com
 * @date 2024/06/14 14:12
 */
@RestController
@RequestMapping("/assets/assetHighlight")
public class AssetHighlightController {
    @Autowired
    private AssetHighlightService assetHighlightService;

    @RequestMapping("/{assetId}")
    private R getByAssetId(@PathVariable("assetId") String assetId) {
        return R.ok().put("data", assetHighlightService.list(new LambdaQueryWrapper<AssetHighlight>().eq(AssetHighlight::getAssetId, assetId)));
    }
}
