package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 各投资人拟投资金额
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:46
 */
@Data
@TableName("investor_asset_operation_model")
public class InvestorAssetOperationModelEntity {
    // 主键
    @TableId
    private Long id;

    // 投资人id
    private Long investorId;

    // 拟投资金额
    private String value;
}
