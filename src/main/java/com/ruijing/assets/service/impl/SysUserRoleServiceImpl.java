package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.SysUserRoleDao;
import com.ruijing.assets.entity.pojo.SysUserRoleEntity;
import com.ruijing.assets.service.SysUserRoleService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserRoleEntity> page = this.page(
                new Query<SysUserRoleEntity>().getPage(params),
                new QueryWrapper<SysUserRoleEntity>()
        );

        return new PageUtils(page);
    }

}
