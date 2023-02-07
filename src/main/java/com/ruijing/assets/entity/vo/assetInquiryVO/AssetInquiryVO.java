package com.ruijing.assets.entity.vo.assetInquiryVO;

import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.AssetInquiryEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import lombok.Data;


@Data
public class AssetInquiryVO extends AssetInquiryEntity {
    //客户信息
    private CustomerEntity customerEntity;
    //资产信息
    private AssetEntity assetEntity;
}
