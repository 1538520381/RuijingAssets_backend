package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 资产运行模式
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:23
 */
@Data
@TableName("asset_operation_model")
public class AssetOperationModelEntity {
    // 主键
    @TableId
    private Long id;

    // 资产运行模式
    private String name;
}
