package com.ruijing.assets.entity.customerCollectionVO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruijing.assets.entity.pojo.AssetCustomerCollectionEntity;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.CustomerEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
public class AssetCustomerCollectionVO {
    //客户信息
    private CustomerEntity customerEntity;
    //资产信息
    private AssetEntity assetEntity;
    //收藏时间
    private Date collectionTime;
}
