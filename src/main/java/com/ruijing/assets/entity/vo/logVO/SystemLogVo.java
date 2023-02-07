package com.ruijing.assets.entity.vo.logVO;

import com.ruijing.assets.entity.pojo.SysLogEntity;
import lombok.Data;

import java.util.List;


//返回给页面的日志信息
@Data
public class SystemLogVo extends SysLogEntity {
    //用户对应的角色名称
    private List<String> roleNames;
}
