package com.ruijing.assets.entity.springsecurityentity;


import com.ruijing.assets.entity.pojo.SysUserEntity;
import lombok.Data;

import java.util.List;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 权限系统中的用户上下文对象
 * 里面封装了 用户的基本信息
 * 用户的权限列表
 * @email 3161788646@qq.com
 * @date 2023/1/31 18:22
 */

@Data
public class ContextUserInfo extends SysUserEntity {
    //某角色所对应的权限
    private List<MenuVo> menuVoList;
}
