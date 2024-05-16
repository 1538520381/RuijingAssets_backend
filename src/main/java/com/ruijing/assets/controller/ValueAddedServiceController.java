package com.ruijing.assets.controller;

import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.ValueAddedServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description ValueAddedService Controller
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:53
 */
@RestController
@RequestMapping("/assets/valueAddedService")
public class ValueAddedServiceController {
    @Autowired
    private ValueAddedServiceService valueAddedServiceService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取增值服务列表
     * @email 1538520381@qq.com
     * @date 2024/5/7 上午10:19
     */
    @GetMapping
    private R getList() {
        return R.success("获取成功").put("valueAddedService", valueAddedServiceService.list());
    }
}
