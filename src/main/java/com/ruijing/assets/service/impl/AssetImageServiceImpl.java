package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetImageDao;
import com.ruijing.assets.entity.pojo.AssetImageEntity;
import com.ruijing.assets.service.AssetImageService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("assetImageService")
public class AssetImageServiceImpl extends ServiceImpl<AssetImageDao, AssetImageEntity> implements AssetImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AssetImageEntity> page = this.page(
                new Query<AssetImageEntity>().getPage(params),
                new QueryWrapper<AssetImageEntity>()
        );

        return new PageUtils(page);
    }

}
