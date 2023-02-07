package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.SysLogEntity;
import com.ruijing.assets.entity.queryDTO.SysLogQueryDTO;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

public interface SysLogService extends IService<SysLogEntity> {
    PageUtils queryPage(Map<String, Object> params, SysLogQueryDTO sysLogQueryDTO);
}
