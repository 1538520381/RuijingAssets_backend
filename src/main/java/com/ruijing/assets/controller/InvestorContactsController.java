package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.entity.pojo.InvestorContacts;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.InvestorContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/08/06 00:15
 */
@RestController
@RequestMapping("/assets/investorContacts")
public class InvestorContactsController {
    @Autowired
    private InvestorContactsService investorContactsService;

    @DeleteMapping("/{id}")
    private R deleteInvestorContacts(@PathVariable Long id) {
        investorContactsService.removeById(id);
        return R.ok();
    }

    @PostMapping
    private R addInvestorContact(@RequestBody InvestorContacts investorContacts) {
        LambdaQueryWrapper<InvestorContacts> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(InvestorContacts::getInvestorId, investorContacts.getInvestorId());
        lambdaQueryWrapper.eq(InvestorContacts::getContacts, investorContacts.getContacts());
        List<InvestorContacts> list = investorContactsService.list(lambdaQueryWrapper);
        if (!list.isEmpty()) {
            return R.error("该联系人已存在，新增失败");
        }
        LambdaQueryWrapper<InvestorContacts> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(InvestorContacts::getInvestorId, investorContacts.getInvestorId());
        lambdaQueryWrapper1.eq(InvestorContacts::getPhone, investorContacts.getPhone());
        List<InvestorContacts> list1 = investorContactsService.list(lambdaQueryWrapper1);
        if (!list1.isEmpty()) {
            return R.error("该联系方式已存在，新增失败");
        }
        investorContactsService.save(investorContacts);
        return R.ok();
    }
}
