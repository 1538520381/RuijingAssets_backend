package com.ruijing.assets.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/21 13:37
 */
@Data
public class TraceAssetDto{
    @TableId
    private Long id;

    private String name;

    private Long traceId;

    private Long createUser;

    private String createUserName;
}
