package com.ruijing.assets.controller;

import com.ruijing.assets.entity.pojo.AssetInquiryEntity;
import com.ruijing.assets.entity.queryDTO.AssetInquiryDTO;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.AssetInquiryService;
import com.ruijing.assets.util.using.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 客户询价表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-27 00:09:01
 */
@RestController
@RequestMapping("assets/assetinquery")
public class AssetInquiryController {
    @Autowired
    private AssetInquiryService assetInqueryService;

    /*
     * @author: K0n9D1KuA
     * @description: 查看询价列表
     * @param: params
     * @param: assetInquiryDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 18:56
     */

    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody AssetInquiryDTO assetInquiryDTO) {
        PageUtils page = assetInqueryService.queryPage(params, assetInquiryDTO);
        return R.ok().put("page", page);
    }


}
