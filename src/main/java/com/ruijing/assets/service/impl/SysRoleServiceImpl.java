package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.SysRoleDao;
import com.ruijing.assets.entity.dto.RoleDTO;
import com.ruijing.assets.entity.pojo.SysMenuEntity;
import com.ruijing.assets.entity.pojo.SysRoleEntity;
import com.ruijing.assets.entity.pojo.SysRoleMenuEntity;
import com.ruijing.assets.entity.pojo.SysUserRoleEntity;
import com.ruijing.assets.entity.vo.roleVO.SysRoleVo;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.SysMenuService;
import com.ruijing.assets.service.SysRoleMenuService;
import com.ruijing.assets.service.SysRoleService;
import com.ruijing.assets.service.SysUserRoleService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {


    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysUserRoleService sysUserRoleService;


    @Autowired
    @Lazy
    SysRoleService sysRoleService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleEntity> page = this.page(
                new Query<SysRoleEntity>().getPage(params),
                new QueryWrapper<SysRoleEntity>()
        );

        return new PageUtils(page);
    }

    /*
     * @author: K0n9D1KuA
     * @description: 添加角色 并且绑定角色和权限的关系
     * @param: roleDTO 角色
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/1/31 22:59
     */
    @Override
    @Transactional
    public void addRole(RoleDTO roleDTO) {
        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(roleDTO, sysRoleEntity);
        sysRoleEntity.setCreatedTime(new Date());
        sysRoleEntity.setUpdatedTime(new Date());
        this.save(sysRoleEntity);
        //绑定角色和权限之间的关系
        if (!CollectionUtils.isEmpty(roleDTO.getMenuIds())) {
            sysRoleService.bindRoleAndMenu(roleDTO);
        }
    }

    //给某个角色分配权限
    @Override
    public void deliverMenu(RoleDTO roleDTO) {
        //绑定角色和权限之间的关系
        if (!CollectionUtils.isEmpty(roleDTO.getMenuIds())) {
            sysRoleService.bindRoleAndMenu(roleDTO);
        }
    }

    //移除角色的权限
    //roleDTO roleDTO 内含 id  menuIds（要移除的权限的id）
    @Override
    public void removeMenu(RoleDTO roleDTO) {
        //判断是否为空？
        if (!CollectionUtils.isEmpty(roleDTO.getMenuIds())) {
            //移除角色和权限之间的关系
            this.removeRoleAndMenu(roleDTO);
        }
    }


    @Transactional
    @Override
    public void removeRole(Long roleId) {
        //首先查询是否有某用户绑定了该角色
        LambdaQueryWrapper<SysUserRoleEntity> sysUserRoleEntityLambdaQueryWrapper
                = new LambdaQueryWrapper<>();
        sysUserRoleEntityLambdaQueryWrapper.eq(SysUserRoleEntity::getSysRoleId, roleId);
        int count = sysUserRoleService.count(sysUserRoleEntityLambdaQueryWrapper);
        if (count > 0) {
            //该角色已被其他用户绑定
            throw new RuiJingException(
                    RuiJingExceptionEnum.ROLE_ALREADY_BIND.getMsg(),
                    RuiJingExceptionEnum.ROLE_ALREADY_BIND.getCode()
            );
        }

        //删除角色表
        this.removeById(roleId);

        //删除角色和权限的绑定关系
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(roleId);
        this.removeRoleAndMenu(roleDTO);
    }

    @Override
    public SysRoleVo getRoleInfo(Long roleId) {
        SysRoleEntity sysRoleEntity = this.getById(roleId);
        SysRoleVo result = new SysRoleVo();
        BeanUtils.copyProperties(sysRoleEntity, result);
        //查询其所有的权限
        LambdaQueryWrapper<SysRoleMenuEntity> sysRoleMenuEntityLambdaQueryWrapper
                = new LambdaQueryWrapper<>();
        sysRoleMenuEntityLambdaQueryWrapper
                .eq(SysRoleMenuEntity::getSysRoleId, roleId);
        List<SysRoleMenuEntity> sysRoleMenuEntities =
                sysRoleMenuService.list(sysRoleMenuEntityLambdaQueryWrapper);
        //收集menuIds
        List<Long> menuIdS = sysRoleMenuEntities.stream()
                .map(sysRoleMenuEntity -> sysRoleMenuEntity.getSysMenuId())
                .collect(Collectors.toList());
        List<SysMenuEntity> sysMenuEntities = null;
        if (!CollectionUtils.isEmpty(menuIdS)) {
            //如果该角色有权限
            sysMenuEntities = sysMenuService.list(new LambdaQueryWrapper<SysMenuEntity>()
                    .in(SysMenuEntity::getId, menuIdS));
            //需要过滤掉一级菜单
            sysMenuEntities = sysMenuEntities.stream()
                    .filter(sysMenuEntity -> sysMenuEntity.getParentId() != 0)
                    .collect(Collectors.toList());
            result.setSysMenuEntityList(sysMenuEntities);
        }
        return result;
    }


    //移除角色和权限之间的关系
    //roleDTO roleDTO 内含 id  menuIds（要移除的权限的id）
    private void removeRoleAndMenu(RoleDTO roleDTO) {
        LambdaQueryWrapper<SysRoleMenuEntity> sysRoleMenuEntityLambdaQueryWrapper
                = new LambdaQueryWrapper<>();

        sysRoleMenuEntityLambdaQueryWrapper
                .eq(
                        roleDTO.getId() != 0,
                        SysRoleMenuEntity::getSysRoleId,
                        roleDTO.getId())
                .in(
                        !CollectionUtils.isEmpty(roleDTO.getMenuIds()),
                        SysRoleMenuEntity::getSysMenuId,
                        roleDTO.getMenuIds());

        //删除
        sysRoleMenuService.remove(sysRoleMenuEntityLambdaQueryWrapper);

    }


    //绑定角色和权限之间的关系
    //添加了多字段联合唯一 保证接口幂等性。
    //alter table sys_role_menu add unique key role_menu_unique (role_id,menu_id)
    @Transactional
    public void bindRoleAndMenu(RoleDTO roleDTO) {
        List<SysRoleMenuEntity> sysRoleMenuEntityListToBeSaved = new ArrayList<>();
        //获得其绑定的权限id
        roleDTO.getMenuIds().forEach(menuId -> {
            //查询该权限的父权限是否已经和该角色绑定
            SysMenuEntity sysMenuEntity = sysMenuService.getById(menuId);
            Long parentId = sysMenuEntity.getParentId();
            int count = sysRoleMenuService.count(new LambdaQueryWrapper<SysRoleMenuEntity>()
                    .eq(SysRoleMenuEntity::getSysMenuId, parentId)
                    .eq(SysRoleMenuEntity::getSysRoleId, roleDTO.getId()));
            if (count == 0) {
                //需要绑定一级菜单和该角色之间的关系
                SysRoleMenuEntity sysRoleMenuEntity = SysRoleMenuEntity.builder()
                        .sysRoleId(roleDTO.getId())
                        .sysMenuId(parentId)
                        .build();
                sysRoleMenuEntityListToBeSaved.add(sysRoleMenuEntity);
            }

            //绑定角色和权限之间的关系
            SysRoleMenuEntity sysRoleMenuEntity = SysRoleMenuEntity.builder()
                    .sysRoleId(roleDTO.getId())
                    .sysMenuId(menuId)
                    .build();
            sysRoleMenuEntityListToBeSaved.add(sysRoleMenuEntity);
        });
        //批量保存
        try {
            sysRoleMenuService.saveBatch(sysRoleMenuEntityListToBeSaved);
        } catch (Exception e) {
            //给一个角色分配了重复的权限
            throw new RuiJingException(
                    RuiJingExceptionEnum.ADD_DUPLICATION_MENU_TO_ONE_ROLE.getMsg(),
                    RuiJingExceptionEnum.ADD_DUPLICATION_MENU_TO_ONE_ROLE.getCode()
            );
        }

    }

}
