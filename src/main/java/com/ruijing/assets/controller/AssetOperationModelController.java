package com.ruijing.assets.controller;

import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.AssetOperationModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description AssetOperationModel Controller
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:43
 */
@RestController
@RequestMapping("/assets/assetOperationModel")
public class AssetOperationModelController {
    @Autowired
    private AssetOperationModelService assetOperationModelService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取资产运行模式列表
     * @email 1538520381@qq.com
     * @date 2024/5/7 上午10:13
     */
    @GetMapping
    public R getAll() {
        return R.success("获取成功").put("assetOperationModel", assetOperationModelService.list());
    }
}
