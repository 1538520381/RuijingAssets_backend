package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description 增值服务
 * @email 1538520381@qq.com
 * @date 2024/05/06 19:50
 */
@Data
@TableName("value_added_service")
public class ValueAddedServiceEntity {
    // 主键
    @TableId
    private Long id;

    // 增值服务
    private String name;
}
