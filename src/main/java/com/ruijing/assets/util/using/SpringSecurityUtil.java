package com.ruijing.assets.util.using;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.entity.springsecurityentity.LoginUser;
import com.ruijing.assets.entity.pojo.SysRoleEntity;
import com.ruijing.assets.entity.pojo.SysUserRoleEntity;
import com.ruijing.assets.service.SysRoleService;
import com.ruijing.assets.service.SysUserRoleService;
import com.ruijing.assets.util.using.ApplicationContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: springsecurity
 * 1, 方便获得当前用户的各种信息
 * @email 3161788646@qq.com
 * @date 2023/1/31 20:28
 */

public class SpringSecurityUtil {
    //sping容器
    private final static ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();

    //获得当前用户
    public static LoginUser getUser() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser;
    }

    //获得当前用户的id
    public static Long getUserId() {
        LoginUser user = getUser();
        return user.getContextUserInfo().getId();
    }

    //获得当前用户的用户名
    public static String getUserName() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getContextUserInfo().getUserName();
    }

    //获取当前用户的角色名数组
    public static List<String> getUserRoleName() {
        //获得当前用户的id
        Long sysUserId = getUserId();
        //操作mysql
        SysUserRoleService sysUserRoleService = applicationContext.getBean(SysUserRoleService.class);
        SysRoleService sysRoleService = applicationContext.getBean(SysRoleService.class);

        List<SysUserRoleEntity> sysUserRoleEntityList = sysUserRoleService.list(
                new LambdaQueryWrapper<SysUserRoleEntity>()
                        .eq(SysUserRoleEntity::getSysUserId, sysUserId));

        //收集角色id
        List<Long> roleIds = sysUserRoleEntityList
                .stream()
                .map(sysUserRoleEntity -> sysUserRoleEntity.getSysRoleId())
                .collect(Collectors.toList());

        //获得角色id 获得角色实体类
        Collection<SysRoleEntity> sysRoleEntities = sysRoleService.listByIds(roleIds);

        //映射
        List<String> roleNames = sysRoleEntities.stream().map(
                sysRoleEntity -> sysRoleEntity.getName()
        ).collect(Collectors.toList());
        //返回角色名
        return roleNames;
    }
}
