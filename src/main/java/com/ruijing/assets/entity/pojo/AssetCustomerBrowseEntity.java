package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户浏览表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-27 00:20:54
 */
@Data
@TableName("asset_customer_browse")
public class AssetCustomerBrowseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 资产id
     */
    private Long assetId;
    /**
     * 浏览时间
     */
    private Date browseTime;

}
