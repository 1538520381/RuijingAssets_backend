package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 拟投资金额
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:12
 */
@Data
@TableName("investment_amount")
public class InvestmentAmountEntity {
    // 主键
    @TableId
    private Long id;

    // 拟投资金额
    private String name;
}
