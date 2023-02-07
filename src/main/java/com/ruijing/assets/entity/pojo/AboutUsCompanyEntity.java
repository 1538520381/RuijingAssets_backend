package com.ruijing.assets.entity.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("about_us_company")
public class AboutUsCompanyEntity {
    @TableId
    //主键
    private Long id;
    //位置
    private String location;
    //领导人
    private String head;
}
