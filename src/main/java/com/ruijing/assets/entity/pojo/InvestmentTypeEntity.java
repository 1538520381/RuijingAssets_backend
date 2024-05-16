package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 拟投资类型
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:38
 */
@Data
@TableName("investment_type")
public class InvestmentTypeEntity {
    // 主键
    @TableId
    private Long id;

    // 拟投资类型
    private String name;
}
