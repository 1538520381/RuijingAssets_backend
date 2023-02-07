package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("asset_highlight")
public class HighlightEntity {
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
