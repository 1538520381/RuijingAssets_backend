package com.ruijing.assets.entity.vo.assetVO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Highlight {
    //债权亮点标题
    private String highlightTitle;
    //债权亮点内容
    private String highlightContent;
}
