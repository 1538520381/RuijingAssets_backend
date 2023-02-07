package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetCollectionBrowseDao;
import com.ruijing.assets.entity.pojo.AssetCollectionBrowseEntity;
import com.ruijing.assets.service.AssetCollectionBrowseService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("assetCollectionBrowseService")
public class AssetCollectionBrowseServiceImpl extends ServiceImpl<AssetCollectionBrowseDao, AssetCollectionBrowseEntity> implements AssetCollectionBrowseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AssetCollectionBrowseEntity> page = this.page(
                new Query<AssetCollectionBrowseEntity>().getPage(params),
                new QueryWrapper<AssetCollectionBrowseEntity>()
        );

        return new PageUtils(page);
    }

}
