package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 经济类型
 * @email 1538520381@qq.com
 * @date 2024/05/06 18:24
 */
@Data
@TableName("economic_type")
public class EconomicTypeEntity {
    // 主键
    @TableId
    private Long id;

    // 国有经济标识
    private Boolean stateOwnedFlag;

    // 姓名
    private String name;
}
