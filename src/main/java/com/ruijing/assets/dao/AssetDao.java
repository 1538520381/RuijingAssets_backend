package com.ruijing.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruijing.assets.entity.pojo.AssetEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房屋资产基本信息表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@Mapper
public interface AssetDao extends BaseMapper<AssetEntity> {

}
