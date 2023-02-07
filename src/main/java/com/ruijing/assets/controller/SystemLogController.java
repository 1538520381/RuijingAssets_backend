package com.ruijing.assets.controller;


import com.ruijing.assets.entity.queryDTO.SysLogQueryDTO;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.SysLogService;
import com.ruijing.assets.util.using.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 系统日志
 * @email 3161788646@qq.com
 * @date 2023/2/7 16:43
 */

@RestController
@RequestMapping("assets/sysLog")
public class SystemLogController {

    @Autowired
    private SysLogService sysLogService;

    /*
     * @author: K0n9D1KuA
     * @description:
     * @param: params
     * @param: sysLogQueryDTO
     * 1，sysLogQueryDTO
     * 按照用户名查（模糊） ->userName = ? userName是null 或者 "" 那么就不拼接
     * 按照时间升序 or 降序  -> timeOrder = 0(升序) 1(降序)
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:06
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody SysLogQueryDTO sysLogQueryDTO) {
        PageUtils page = sysLogService.queryPage(params, sysLogQueryDTO);
        return R.ok().put("page", page);
    }

}
