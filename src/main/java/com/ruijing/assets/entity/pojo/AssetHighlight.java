package com.ruijing.assets.entity.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("asset_highlight")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetHighlight implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    @TableId
    private Long id;
    //债权亮点标题
    private String highlightTitle;
    //债权亮点内容
    private String highlightContent;
    //债权id
    private Long assetId;
}
