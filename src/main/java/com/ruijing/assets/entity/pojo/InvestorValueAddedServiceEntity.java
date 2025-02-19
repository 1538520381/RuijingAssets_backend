package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 各投资人所需增值服务
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:54
 */
@Data
@TableName("investor_value_added_service")
public class InvestorValueAddedServiceEntity {
    // 主键
    @TableId
    private Long id;

    // 投资人id
    private Long investorId;

    // 增值服务
    private String value;
}
