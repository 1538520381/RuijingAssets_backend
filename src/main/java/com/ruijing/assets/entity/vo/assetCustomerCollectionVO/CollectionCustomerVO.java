package com.ruijing.assets.entity.vo.assetCustomerCollectionVO;

import com.ruijing.assets.entity.pojo.CustomerEntity;
import lombok.Data;

import java.util.Date;


@Data
public class CollectionCustomerVO extends CustomerEntity {
    //收藏时间
    private Date collectionTime;
}
