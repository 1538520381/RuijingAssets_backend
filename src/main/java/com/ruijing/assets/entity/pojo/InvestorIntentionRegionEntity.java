package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 各投资人意向投资区域
 * @email 1538520381@qq.com
 * @date 2024/06/13 11:32
 */
@Data
@TableName("investor_intention_region")
public class InvestorIntentionRegionEntity {
    // 主键
    @TableId
    private Long id;

    // 投资人主键
    private Long investorId;

    // 意向投资区域
    private String value;
}
