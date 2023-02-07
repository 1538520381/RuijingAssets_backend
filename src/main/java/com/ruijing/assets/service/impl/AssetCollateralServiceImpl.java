package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetCollateralDao;
import com.ruijing.assets.entity.pojo.AssetCollateralEntity;
import com.ruijing.assets.service.AssetCollateralService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("assetCollateralService")
public class AssetCollateralServiceImpl extends ServiceImpl<AssetCollateralDao, AssetCollateralEntity> implements AssetCollateralService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AssetCollateralEntity> page = this.page(
                new Query<AssetCollateralEntity>().getPage(params),
                new QueryWrapper<AssetCollateralEntity>()
        );

        return new PageUtils(page);
    }

}
