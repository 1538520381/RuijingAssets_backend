package com.ruijing.assets.entity.dto;


import lombok.Data;

@Data
public class CollectionDTO {
    //债权id
    private Long assetId;
    //收藏或者取消收藏
    private Boolean collectionOrNot;
}
