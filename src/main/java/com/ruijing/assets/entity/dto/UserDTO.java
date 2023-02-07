package com.ruijing.assets.entity.dto;

import com.ruijing.assets.entity.pojo.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends SysUserEntity {
    //该用户所对应的角色id
    private List<Long> roleIds;

}
