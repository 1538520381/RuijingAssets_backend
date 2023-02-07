package com.ruijing.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruijing.assets.entity.pojo.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 绑定角色和菜单之间的关系
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:43
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

}
