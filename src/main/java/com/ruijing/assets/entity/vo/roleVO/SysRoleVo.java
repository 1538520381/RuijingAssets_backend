package com.ruijing.assets.entity.vo.roleVO;

import com.ruijing.assets.entity.pojo.SysMenuEntity;
import com.ruijing.assets.entity.pojo.SysRoleEntity;
import lombok.Data;

import java.util.List;


@Data
public class SysRoleVo extends SysRoleEntity {
    //该角色所对应的全部权限
    private List<SysMenuEntity> sysMenuEntityList;
}
