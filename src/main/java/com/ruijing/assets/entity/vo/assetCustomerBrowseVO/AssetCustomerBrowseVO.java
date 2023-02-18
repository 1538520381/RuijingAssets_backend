package com.ruijing.assets.entity.vo.assetCustomerBrowseVO;


import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.vo.assetCustomerCollectionVO.CollectionCustomerVO;
import lombok.Data;

import java.util.List;

@Data
public class AssetCustomerBrowseVO {

    //该资产下的所有客户信息
    private List<BrowseCustomerVO> customerEntityList;
    //资产信息
    private AssetEntity assetEntity;
    //浏览量
    private Long browseNum;
}
