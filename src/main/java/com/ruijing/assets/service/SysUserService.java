package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.dto.UpdatePasswordDTO;
import com.ruijing.assets.entity.dto.UserDTO;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.entity.vo.userVO.UserVo;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 用户表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:42
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addUser(UserDTO userDTO);

    void deliverRole(UserDTO userDTO);

    void removeRole(UserDTO userDTO);

    void removeUser(Long userId);

    UserVo userInfo(Long userId);

    void updatePassword(UpdatePasswordDTO updatePasswordDTO);
}

