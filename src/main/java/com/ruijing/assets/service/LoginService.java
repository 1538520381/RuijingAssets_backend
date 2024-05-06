package com.ruijing.assets.service;

import com.ruijing.assets.entity.dto.LoginUserDTO;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.entity.result.R;

public interface LoginService {


    R loginAdmin(LoginUserDTO loginUserDTO);

    R logout(String token);

    R register(SysUserEntity sysUserEntity);
}
