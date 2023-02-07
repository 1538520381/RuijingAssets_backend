package com.ruijing.assets.entity.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("contact_us")
@Data
public class ContactUsEntity {
    //主键
    @TableId
    private Long id;
    //内容
    private String contentText;
    //公司简介
    private String companyProfile;
    //图片
    private String image;
}
