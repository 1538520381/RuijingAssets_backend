package com.ruijing.assets.controller;

import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.InvestmentAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description InvestmentAmount Controller
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:16
 */
@RestController
@RequestMapping("/assets/investmentAmount")
public class InvestmentAmountController {
    @Autowired
    private InvestmentAmountService investmentAmountService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取拟投资金额列表
     * @email 1538520381@qq.com
     * @date 2024/5/7 上午10:15
     */
    @GetMapping
    private R getList() {
        return R.success("获取成功").put("investmentAmount", investmentAmountService.list());
    }
}
