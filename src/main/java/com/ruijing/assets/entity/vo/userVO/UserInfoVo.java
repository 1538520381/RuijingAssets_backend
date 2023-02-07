package com.ruijing.assets.entity.vo.userVO;

import com.ruijing.assets.entity.pojo.SysUserEntity;
import lombok.Data;

import java.util.List;


@Data
public class UserInfoVo extends SysUserEntity {
    //对应的角色名
    private List<String> roleNames;
}
