package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AssetGuaranteeDao;
import com.ruijing.assets.entity.pojo.AssetGuaranteeEntity;
import com.ruijing.assets.service.AssetGuaranteeService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("assetGuaranteeService")
public class AssetGuaranteeServiceImpl extends ServiceImpl<AssetGuaranteeDao, AssetGuaranteeEntity> implements AssetGuaranteeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AssetGuaranteeEntity> page = this.page(
                new Query<AssetGuaranteeEntity>().getPage(params),
                new QueryWrapper<AssetGuaranteeEntity>()
        );

        return new PageUtils(page);
    }

}
