package com.ruijing.assets.controller;


import com.ruijing.assets.constant.ruiJingConstant.*;
import com.ruijing.assets.entity.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 系统常量控制器
 * @email 3161788646@qq.com
 * @date 2023/2/7 16:54
 */

@RestController
@RequestMapping("/assets")
public class RuiJingConstantController {

    //省份
    @GetMapping("/provinceMap")
    public R getProvinceMap() {
        return R.success(ProvinceAndCityConstant.provinceAndCityMap);
    }

    //抵押物种类
    @GetMapping("/assetCollateralTypeConstant")
    public R getAssetCollateralTypeConstant() {
        return R.success(AssetCollateralTypeConstant.assetCollateralTypeMap);
    }

    //债权种类常量
    @GetMapping("/AssetTypeConstant")
    public R getAssetTypeConstant() {
        return R.success(AssetTypeConstant.assetTypeConstantMap);
    }

    //处置方式常量
    @GetMapping("/DisposalMethodTypeConstant")
    public R getDisposalMethodTypeConstant() {
        return R.success(DisposalMethodTypeConstant.disposalMethodType);
    }

    //担保人担保方式
    @GetMapping("/GuaranteeGuaranteeMethodConstant")
    public R getGuaranteeGuaranteeMethodConstant() {
        return R.success(GuaranteeGuaranteeMethodConstant.guaranteeGuaranteeMethodMap);
    }

    //资产诉讼状态常量
    @GetMapping("/LitigationStatusConstant")
    public R getLitigationStatusConstant() {
        return R.success(LitigationStatusConstant.litigationStatusMap);
    }


}
