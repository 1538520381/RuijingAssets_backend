package com.ruijing.assets.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruijing.assets.constant.redisConstant.RedisKeyConstant;
import com.ruijing.assets.entity.dto.LoginUserDTO;
import com.ruijing.assets.entity.springsecurityentity.ContextUserInfo;
import com.ruijing.assets.entity.springsecurityentity.MenuVo;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.LoginService;
import com.ruijing.assets.service.SysMenuService;
import com.ruijing.assets.service.SysUserService;
import com.ruijing.assets.util.using.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * @author: K0n9D1KuA
     * @description: 后台管理用户登录
     * 需要返回的  用户token  用户的权限菜单
     * @param: loginUserDTO
     * @date: 2023/1/31 15:25
     */

    @Override
    public R loginAdmin(LoginUserDTO loginUserDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.getUserName(), loginUserDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            //账号或者密码错误
            throw new RuiJingException(RuiJingExceptionEnum.PASSWORD_OR_USERNAME_WRONG.getMsg(),
                    RuiJingExceptionEnum.PASSWORD_OR_USERNAME_WRONG.getCode());
        }
        //密码校验通过
        //生成随机uuid 转换为 token 存入redis中
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String token = JwtUtil.createJWT(uuid);
        String redisKey = RedisKeyConstant.LOGIN_USER_KEY + uuid;
        //将用户信息存入redis中
        //创建ContextUserInfo对象  上下文中用户的所有信息封装为此对象
        ContextUserInfo contextUserInfo = new ContextUserInfo();
        //校验通过 查询数据库获得用户的基本信息
        SysUserEntity sysUserEntity = sysUserService.getOne(
                new LambdaQueryWrapper<SysUserEntity>()
                        .eq(SysUserEntity::getUserName, loginUserDTO.getUserName()));
        BeanUtils.copyProperties(sysUserEntity, contextUserInfo);
        //查询其权限信息
        List<MenuVo> menuVoList = sysMenuService.selectMenuVoList(sysUserEntity.getUserName());
        contextUserInfo.setMenuVoList(menuVoList);
        //将用户信息存为json
        String contextUserInfoString = JSON.toJSONString(contextUserInfo);
        stringRedisTemplate.opsForValue().set(redisKey, contextUserInfoString);
        //返回结果
        return R.ok()
                .put("token", token)
                .put("userInfo", contextUserInfo);
    }

    @Override
    public R logout(String token) {
        Claims claims = null;
        //获得解析出来的uuid
        try {
            claims = JwtUtil.parseJWT(token);
            String uuid = claims.getSubject();
            //删除redis中的数据
            stringRedisTemplate.delete(RedisKeyConstant.LOGIN_USER_KEY + uuid);
            return R.ok();
        } catch (Exception e) {
            //退出登录失败
            throw new RuiJingException(RuiJingExceptionEnum.LOGIN_FAILED.getMsg(),
                    RuiJingExceptionEnum.LOGOUT_FAILED.getCode());
        }
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 注册
     * @email 1538520381@qq.com
     * @date 2024/5/5 下午9:43
     */
    @Override
    public R register(SysUserEntity sysUserEntity) {
        sysUserEntity.setPassword(passwordEncoder.encode(sysUserEntity.getPassword()));
        sysUserEntity.setUserStatus(1);
        sysUserService.save(sysUserEntity);
        return R.ok();
    }
}
