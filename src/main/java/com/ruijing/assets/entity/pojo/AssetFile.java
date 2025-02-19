package com.ruijing.assets.entity.pojo;

import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/07/16 02:49
 */
@Data
public class AssetFile {
    private Long id;
    private Long assetId;
    private String originalName;
    private String name;
}
