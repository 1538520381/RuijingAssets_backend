package com.ruijing.assets.entity.vo.assetCustomerCollectionVO;

import com.ruijing.assets.entity.pojo.AssetEntity;
import lombok.Data;

import java.util.List;


@Data
public class AssetCustomerCollectionVO {
    //该资产下的所有客户信息
    private List<CollectionCustomerVO> customerEntityList;
    //资产信息
    private AssetEntity assetEntity;
    //收藏量
    private Long collectionNum;
}
