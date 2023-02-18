package com.ruijing.assets.controller;

import com.ruijing.assets.annotation.SysLog;
import com.ruijing.assets.entity.dto.RoleDTO;
import com.ruijing.assets.entity.pojo.SysRoleEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.entity.vo.roleVO.SysRoleVo;
import com.ruijing.assets.service.SysRoleService;
import com.ruijing.assets.util.using.PageUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 系统角色表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:43
 */
@RestController
@RequestMapping("assets/sysrole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;


    /*
     * @author: K0n9D1KuA
     * @description: 查看全部角色
     * @param: params
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:43
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysRoleService.queryPage(params);
        return R.ok().put("page", page);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 添加角色 并且绑定角色和权限的关系
     * @param: roleDTO 角色
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/1/31 22:59
     */
    @SysLog(operationType = 2, operationName = "创建新角色")
    @PostMapping("/addRole")
    public R addRole(@RequestBody RoleDTO roleDTO) {
        sysRoleService.addRole(roleDTO);
        return R.ok();
    }


    /*
     * @author: K0n9D1KuA
     * @description: 修改角色信息
     * @param: sysRoleEntity
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/18 15:55
     */
    @PostMapping("/updateRole")
    @SysLog(operationType = 2, operationName = "修改角色信息")
    public R updateRole(@RequestBody SysRoleEntity sysRoleEntity) {
        sysRoleService.updateById(sysRoleEntity);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 给某个角色分配权限
     * @param: roleDTO 内含 roleId  menuIds（要绑定的权限的id）
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:14
     */
    @PostMapping("/deliverMenu")
    @SysLog(operationType = 2, operationName = "分配角色权限")
    public R deliverMenu(@RequestBody RoleDTO roleDTO) {
        sysRoleService.deliverMenu(roleDTO);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 移除某个角色的权限
     * @param: roleDTO roleDTO 内含 id  menuIds（要移除的权限的id）
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:25
     */
    @PostMapping("/removeMenu")
    @SysLog(operationType = 2, operationName = "移除角色权限")
    public R removeMenu(@RequestBody RoleDTO roleDTO) {
        sysRoleService.removeMenu(roleDTO);
        return R.ok();
    }


    /*
     * @author: K0n9D1KuA
     * @description: 删除系统角色
     * @param: roleId 角色id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:31
     */

    @PostMapping("/removeRole/{roleId}")
    @SysLog(operationType = 2, operationName = "删除系统角色")
    public R removeRole(@PathVariable Long roleId) {
        sysRoleService.removeRole(roleId);
        return R.ok();
    }


    /*
     * @author: K0n9D1KuA
     * @description: 获得角色的信息
     * @param: roleId 角色id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:31
     */
    @GetMapping("/getRoleInfo/{roleId}")
    public R getRoleInfo(@PathVariable Long roleId) {
        SysRoleVo result = sysRoleService.getRoleInfo(roleId);
        return R.success(result);
    }
}
