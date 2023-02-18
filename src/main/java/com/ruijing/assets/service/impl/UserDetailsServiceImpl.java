package com.ruijing.assets.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.entity.springsecurityentity.ContextUserInfo;
import com.ruijing.assets.entity.springsecurityentity.LoginUser;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.enume.status.UserStatus;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity sysUserEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getUserName, username));


        //全局异常捕捉
        if (Objects.isNull(sysUserEntity)) {
            //该用户不存在
            throw new RuiJingException(
                    RuiJingExceptionEnum.NO_THIS_USER.getMsg(),
                    RuiJingExceptionEnum.NO_THIS_USER.getCode()
            );
        }
        //全局异常捕捉
        if (sysUserEntity.getUserStatus().equals(UserStatus.FORBIDDEN_STATUS.getCode())) {
            //该用户已经被禁用
            throw new RuiJingException(
                    RuiJingExceptionEnum.THE_USER_FORBIDDEN.getMsg(),
                    RuiJingExceptionEnum.THE_USER_FORBIDDEN.getCode()
            );
        }
        ContextUserInfo contextUserInfo = new ContextUserInfo();
        contextUserInfo.setUserName(sysUserEntity.getUserName());
        contextUserInfo.setPassword(sysUserEntity.getPassword());
        return new LoginUser(contextUserInfo);
    }
}
