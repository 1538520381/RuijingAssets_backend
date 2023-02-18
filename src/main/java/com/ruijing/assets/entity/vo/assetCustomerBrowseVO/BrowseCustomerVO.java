package com.ruijing.assets.entity.vo.assetCustomerBrowseVO;

import com.ruijing.assets.entity.pojo.CustomerEntity;
import lombok.Data;

import java.util.Date;


@Data
public class BrowseCustomerVO extends CustomerEntity {
    //浏览时间
    private Date browseTime;
}
