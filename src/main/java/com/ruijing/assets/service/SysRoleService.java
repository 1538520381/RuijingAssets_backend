package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.dto.RoleDTO;
import com.ruijing.assets.entity.pojo.SysRoleEntity;
import com.ruijing.assets.entity.vo.roleVO.SysRoleVo;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 系统角色表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:43
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /*
     * @author: K0n9D1KuA
     * @description: 添加角色 并且绑定角色和权限的关系
     * @param: roleDTO 角色
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/1/31 22:59
     */
    void addRole(RoleDTO roleDTO);

    void deliverMenu(RoleDTO roleDTO);

    void removeMenu(RoleDTO roleDTO);

    void removeRole(Long roleId);

    SysRoleVo getRoleInfo(Long roleId);
}

