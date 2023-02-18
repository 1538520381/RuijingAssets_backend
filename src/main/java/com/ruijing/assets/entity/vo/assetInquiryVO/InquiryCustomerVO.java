package com.ruijing.assets.entity.vo.assetInquiryVO;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class InquiryCustomerVO {

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
}
