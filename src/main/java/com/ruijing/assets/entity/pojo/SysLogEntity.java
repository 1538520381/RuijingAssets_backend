package com.ruijing.assets.entity.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 日志实体类
 */
@Data
@TableName("sys_log")
@ToString
public class SysLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 访问者姓名
     */
    private String userName;
    /**
     * 访问者ip
     */
    private String ip;
    /**
     * 操作内容  登录？ 修改 ？ 登出登录？
     */
    private String operationName;
    /**
     * 访问时间
     */
    private Date visitTime;
}
