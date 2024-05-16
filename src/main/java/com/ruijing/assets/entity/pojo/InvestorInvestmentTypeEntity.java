package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 各投资人拟投资类型
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:58
 */
@Data
@TableName("investor_investment_type")
public class InvestorInvestmentTypeEntity {
    // 主键
    @TableId
    private Long id;

    // 投资人id
    private Long investorId;

    // 拟投资类型id
    private Long investmentTypeId;
}
