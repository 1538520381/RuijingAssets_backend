package com.ruijing.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruijing.assets.entity.pojo.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:42
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> getAllMenu(@Param("userName") String userName);
}
