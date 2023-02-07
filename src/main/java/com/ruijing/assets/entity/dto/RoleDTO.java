package com.ruijing.assets.entity.dto;

import com.ruijing.assets.entity.pojo.SysRoleEntity;
import lombok.Data;

import java.util.List;


@Data
public class RoleDTO extends SysRoleEntity {
    //对应的权限id
    private List<Long> menuIds;
}
