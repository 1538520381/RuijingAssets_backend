package com.ruijing.assets.entity.springsecurityentity;


import com.ruijing.assets.entity.pojo.SysMenuEntity;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo extends SysMenuEntity {
    //它的孩子菜单
    private List<MenuVo> children;
}
