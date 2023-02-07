package com.ruijing.assets.entity.event;

import com.ruijing.assets.entity.pojo.SysLogEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 记录日志事件类
 * @email 3161788646@qq.com
 * @date 2023/2/1 15:15
 */

@Setter
@Getter
public class SysLogEvent extends ApplicationEvent {

    private SysLogEntity sysLogEntity;



    public SysLogEvent(Object source, SysLogEntity sysLogEntity) {
        super(source);
        this.sysLogEntity = sysLogEntity;
    }
}
