package com.ruijing.assets.controller;

import com.ruijing.assets.entity.pojo.AssetCustomerCollectionEntity;
import com.ruijing.assets.entity.queryDTO.AssetCustomerCollectionQueryDTO;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.AssetCustomerCollectionService;
import com.ruijing.assets.util.using.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 客户收藏资产表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-27 00:20:54
 */
@RestController
@RequestMapping("assets/assetcustomercollection")
public class AssetCustomerCollectionController {
    @Autowired
    private AssetCustomerCollectionService assetCustomerCollectionService;

    /*
     * @author: K0n9D1KuA
     * @description: 查询客户收藏资产情况
     * @param: params current size分页参数
     * @param: assetCustomerCollectionQueryDTO 条件查询
     * assetName 资产名字模糊查询  timeOrder 时间升序or降序
     * @return:
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 4:18
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody AssetCustomerCollectionQueryDTO assetCustomerCollectionQueryDTO) {
        PageUtils page = assetCustomerCollectionService.queryPage(params, assetCustomerCollectionQueryDTO);
        return R.ok().put("page", page);
    }


}
