package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序端客户
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@Data
@TableName("customer")
@Builder
public class CustomerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 客户姓名
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 用户微信唯一标识
     */
    private String openId;
    /**
     * 邮箱
     */
    private String email;

}
