package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.SysRoleMenuDao;
import com.ruijing.assets.entity.pojo.SysRoleMenuEntity;
import com.ruijing.assets.service.SysRoleMenuService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleMenuEntity> page = this.page(
                new Query<SysRoleMenuEntity>().getPage(params),
                new QueryWrapper<SysRoleMenuEntity>()
        );

        return new PageUtils(page);
    }

}
