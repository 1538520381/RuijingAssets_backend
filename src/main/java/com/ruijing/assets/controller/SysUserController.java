package com.ruijing.assets.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.annotation.SysLog;
import com.ruijing.assets.entity.dto.UpdatePasswordDTO;
import com.ruijing.assets.entity.dto.UserDTO;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.pojo.InvestorEntity;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.entity.pojo.TraceEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.entity.vo.userVO.UserVo;
import com.ruijing.assets.service.AssetService;
import com.ruijing.assets.service.InvestorService;
import com.ruijing.assets.service.SysUserService;
import com.ruijing.assets.service.TraceService;
import com.ruijing.assets.util.using.PageUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 用户表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:42
 */
@RestController
@RequestMapping("assets/sysuser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private InvestorService investorService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private TraceService traceService;

    /*
     * @author: K0n9D1KuA
     * @description:
     * @param: 查询所有用户
     * @param: params : key 按照用户名模糊查询
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:02
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/captain")
    public R getCaptain() {
        List<SysUserEntity> sysUserEntityList = sysUserService.list(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getAdmin, 2));
        return R.ok().put("data", sysUserEntityList);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 修改用户
     * @param: sysUser 要修改的实体类
     * @date: 2023/2/4 21:05
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysUserEntity sysUser) {
        //如果密码不为空 那么需要对用户的密码进行加密
        if (sysUser.getPassword() != null) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        sysUserService.updateById(sysUser);
        return R.ok();
    }


    /*
     * @author: K0n9D1KuA
     * @description: 新增用户&&绑定用户和角色之间的关系
     * @param: userDTO 用户基本信息 + 用户要绑定的角色id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:56
     */

    @PostMapping("/addUser")
    public R addUser(@RequestBody UserDTO userDTO) {
        sysUserService.addUser(userDTO);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 给某个用户分配角色
     * @param: userDTO 用户id + 要分配的角色的id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:56
     */
    @PostMapping("/deliverRole")
    @SysLog(operationType = 2, operationName = "给用户分配角色身份")
    public R deliverRole(@RequestBody UserDTO userDTO) {
        sysUserService.deliverRole(userDTO);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 给某个用户移除角色
     * @param: userDTO 用户id + 要删除的角色的id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:56
     */
    @PostMapping("/removeRole")
    @SysLog(operationType = 2, operationName = "移除用户的角色身份")
    public R removeRole(@RequestBody UserDTO userDTO) {
        sysUserService.removeRole(userDTO);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 删除某个角色
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:56
     */
    @PostMapping("/removeUser/{userId}")
    @SysLog(operationType = 2, operationName = "删除用户")
    public R removeUser(@PathVariable Long userId) {
        List<InvestorEntity> list = investorService.list(new LambdaQueryWrapper<InvestorEntity>().eq(InvestorEntity::getCreateUser, userId));
        if (!list.isEmpty()) {
            return R.error("该用户导入过投资人，无法删除");
        } else {
            List<AssetEntity> list1 = assetService.list(new LambdaQueryWrapper<AssetEntity>().eq(AssetEntity::getCreateUser, userId));
            if (!list1.isEmpty()) {
                return R.error("该用户导入过债权，无法删除");
            } else {
                List<TraceEntity> list2 = traceService.list(new LambdaQueryWrapper<TraceEntity>().eq(TraceEntity::getUserId, userId));
                if (!list1.isEmpty()) {
                    return R.error("该用户进行过追踪操作，无法删除");
                }
            }
        }

        sysUserService.removeUser(userId);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description: 查看某个用户的详细信息
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 2:56
     */
    @GetMapping("/userInfo/{userId}")
    public R userInfo(@PathVariable Long userId) {
        UserVo userVo = sysUserService.userInfo(userId);
        return R.success(userVo);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 个人中心修改密码
     * @param: updatePasswordDTO 要修改的密码
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:44
     */
    @PostMapping("/updatePassword")
    public R updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        //修改密码
        sysUserService.updatePassword(updatePasswordDTO);
        return R.ok();
    }
}
