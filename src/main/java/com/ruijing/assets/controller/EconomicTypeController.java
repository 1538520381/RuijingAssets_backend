package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.entity.pojo.EconomicTypeEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.EconomicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Persolute
 * @version 1.0
 * @description EconomicType Controller
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:27
 */
@RestController
@RequestMapping("/assets/economicType")
public class EconomicTypeController {
    @Autowired
    private EconomicTypeService economicTypeService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取经济类型列表
     * @email 1538520381@qq.com
     * @date 2024/5/7 上午10:14
     */
    @GetMapping
    private R getAll() {
        List<EconomicTypeEntity> stateOwnedList = economicTypeService.list(new LambdaQueryWrapper<EconomicTypeEntity>().eq(EconomicTypeEntity::getStateOwnedFlag, true));
        List<EconomicTypeEntity> unStateOwnedList = economicTypeService.list(new LambdaQueryWrapper<EconomicTypeEntity>().eq(EconomicTypeEntity::getStateOwnedFlag, false));

        Map<String, Object> stateOwnedMap = new HashMap<>();
        stateOwnedMap.put("name", "国有经济");
        stateOwnedMap.put("children", stateOwnedList);

        Map<String, Object> unStateOwnedMap = new HashMap<>();
        unStateOwnedMap.put("name", "非国有经济");
        unStateOwnedMap.put("children", unStateOwnedList);

        List<Map<String, Object>> economicType = new ArrayList<>();
        economicType.add(stateOwnedMap);
        economicType.add(unStateOwnedMap);

        return R.success("获取成功").put("economicType", economicType);
    }
}
