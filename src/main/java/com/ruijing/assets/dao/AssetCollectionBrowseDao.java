package com.ruijing.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruijing.assets.entity.pojo.AssetCollectionBrowseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 每个资产浏览量与收藏量
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@Mapper
public interface AssetCollectionBrowseDao extends BaseMapper<AssetCollectionBrowseEntity> {

}
