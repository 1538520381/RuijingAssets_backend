package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.SysUserRoleEntity;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 用户和角色之间的绑定关系
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:43
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

