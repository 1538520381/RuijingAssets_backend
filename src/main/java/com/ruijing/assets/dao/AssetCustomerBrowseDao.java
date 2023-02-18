package com.ruijing.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruijing.assets.entity.pojo.AssetCollectionBrowseEntity;
import com.ruijing.assets.entity.pojo.AssetCustomerBrowseEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AssetCustomerBrowseDao extends BaseMapper<AssetCustomerBrowseEntity> {

}
