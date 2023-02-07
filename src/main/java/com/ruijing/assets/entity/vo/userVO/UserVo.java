package com.ruijing.assets.entity.vo.userVO;

import com.ruijing.assets.entity.pojo.SysRoleEntity;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import lombok.Data;

import java.util.List;


@Data
public class UserVo extends SysUserEntity {
    //该用户所对应的角色
    private List<SysRoleEntity> roles;
}
