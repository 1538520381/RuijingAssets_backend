package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.InvestorDao;
import com.ruijing.assets.entity.dto.TraceDto;
import com.ruijing.assets.entity.pojo.*;
import com.ruijing.assets.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Investor ServiceImpl
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:36
 */
@Service
public class InvestorServiceImpl extends ServiceImpl<InvestorDao, InvestorEntity> implements InvestorService {
//    @Autowired
//    private AssetService assetService;
//
//    @Autowired
//    private AssetCollateralService assetCollateralService;
//
//    @Autowired
//    private InvestorInvestmentAmountService investorInvestmentAmountService;
//
//    @Autowired
//    private InvestorInvestmentTypeService investorInvestmentTypeService;
//    @Autowired
//    private TraceService traceService;
//
//    @Autowired
//    private SysUserService sysUserService;
}
    /*
     * @author Persolute
     * @version 1.0
     * @description 投资人匹配
     * @email 1538520381@qq.com
     * @date 2024/5/25 下午1:05
     */

