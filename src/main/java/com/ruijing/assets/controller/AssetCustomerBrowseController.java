package com.ruijing.assets.controller;

import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.AssetCustomerBrowseService;
import com.ruijing.assets.service.AssetInquiryService;
import com.ruijing.assets.util.using.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 客户浏览表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-27 00:09:01
 */
@RestController
@RequestMapping("assets/assetCustomerBrowse")
public class AssetCustomerBrowseController {
    @Autowired
    private AssetCustomerBrowseService assetCustomerBrowseService;

    /*
     * @author: K0n9D1KuA
     * @description: 查看客户浏览列表
     * @param: params
     * @param: assetInquiryDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 18:56
     */

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = assetCustomerBrowseService.queryPage(params);
        return R.ok().put("page", page);
    }


}
