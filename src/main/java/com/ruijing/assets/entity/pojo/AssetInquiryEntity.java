package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 客户询价表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-27 00:09:01
 */
@Data
@TableName("asset_inquiry")
public class AssetInquiryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 客户id
     */
    private Long inquiryCustomerId;
    /**
     * 询价客户姓名
     */
    private String inquiryName;
    /**
     * 询价公司名字
     */
    private String inquiryCompanyName;
    /**
     * 询价电话号码
     */
    private String inquiryPhone;
    /**
     * 询价邮箱
     */
    private String inquiryEmail;
    /**
     * 询价备注
     */
    private String inquiryRemark;
    /**
     * 询价时间
     */
    private Date inquiryTime;
    /**
     * 债权id
     */
    private Long assetId;
}
