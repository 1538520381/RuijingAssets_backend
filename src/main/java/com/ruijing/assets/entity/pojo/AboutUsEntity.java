package com.ruijing.assets.entity.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("about_us")
public class AboutUsEntity {
    //主键
    @TableId
    private Long id;
    //传真
    private String fax;
    //邮箱
    private String email;
    //电话
    private String phone;
}
