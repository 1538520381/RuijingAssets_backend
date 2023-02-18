package com.ruijing.assets.controller;

import com.ruijing.assets.entity.pojo.SysMenuEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.SysMenuService;
import com.ruijing.assets.util.using.PageUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 系统菜单
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:42
 */
@RestController
@RequestMapping("assets/sysmenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /*
     * @author: K0n9D1KuA
     * @description: 返回全部权限
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:33
     */
    @GetMapping("/list")
    public R list() {
        List<SysMenuEntity> result = sysMenuService.list();
        //过滤掉一级菜单
        result = result
                .stream()
                .filter(sysMenuEntity -> sysMenuEntity.getParentId() != 0).collect(Collectors.toList());
        return
                R.success(result);
    }

}
