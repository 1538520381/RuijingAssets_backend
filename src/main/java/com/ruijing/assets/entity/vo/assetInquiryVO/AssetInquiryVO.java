package com.ruijing.assets.entity.vo.assetInquiryVO;

import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.AssetInquiryEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import lombok.Data;

import java.util.List;


@Data
public class AssetInquiryVO {
    //客户信息
    private List<InquiryCustomerVO> inquiryCustomerVOList;
    //资产信息
    private AssetEntity assetEntity;
    //询价数量
    private Long inquiryNum;
}
