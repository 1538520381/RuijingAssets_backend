package com.ruijing.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruijing.assets.entity.pojo.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色之间的绑定关系
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:43
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

}
