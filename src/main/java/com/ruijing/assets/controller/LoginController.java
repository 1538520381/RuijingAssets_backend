package com.ruijing.assets.controller;

import com.ruijing.assets.annotation.SysLog;
import com.ruijing.assets.entity.dto.LoginUserDTO;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 管理后台登录
 * @email 3161788646@qq.com
 * @date 2023/2/7 16:53
 */
@Slf4j
@RestController
@RequestMapping("assets/customer")
public class LoginController {


    @Autowired
    LoginService loginService;
    /*
     * @author: K0n9D1KuA
     * @description: 管理后台用户登录
     * @param: loginUserDTO  管理后台用户登录实体类
     * @date: 2023/1/31 15:45
     */

    @SysLog(operationName = "登录", operationType = 1)
    @PostMapping("/login")
    public R login(@RequestBody LoginUserDTO loginUserDTO) {
        return loginService.loginAdmin(loginUserDTO);
    }

    /*
     * @author: K0n9D1KuA
     * @description: 管理后台用户退出登录
     * @param: loginUserDTO  管理后台用户登录实体类
     * @date: 2023/1/31 15:45
     */

    @SysLog(operationName = "退出登录", operationType = 2)
    @PostMapping("/logout")
    public R logout(@RequestHeader("token") String token) {
        return loginService.logout(token);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 注册
     * @email 1538520381@qq.com
     * @date 2024/5/5 下午9:40
     */
    @PostMapping("/register")
    public R register(@RequestBody SysUserEntity sysUserEntity) {
        return loginService.register(sysUserEntity);
    }
}
