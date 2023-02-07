package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.springsecurityentity.MenuVo;
import com.ruijing.assets.entity.pojo.SysMenuEntity;
import com.ruijing.assets.util.using.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:42
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MenuVo> selectMenuVoList(String userName);
}

