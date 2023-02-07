package com.ruijing.assets.entity.queryDTO;


import lombok.Data;

@Data
public class AssetCustomerCollectionQueryDTO {
    //按照时间升序 or 降序  -> timeOrder = 0(升序) 1(降序)
    private Integer timeOrder;
}
