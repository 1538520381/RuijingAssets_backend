package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.SysMenuDao;
import com.ruijing.assets.entity.springsecurityentity.MenuVo;
import com.ruijing.assets.entity.pojo.SysMenuEntity;
import com.ruijing.assets.service.SysMenuService;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //不要分页 全部查出来
        IPage<SysMenuEntity> page = this.page(
                new Query<SysMenuEntity>().getPage(params),
                new QueryWrapper<SysMenuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MenuVo> selectMenuVoList(String userName) {
        //首先查询该角色的所有权限
        SysMenuDao baseMapper = this.getBaseMapper();
        List<SysMenuEntity> sysMenuEntities = baseMapper.getAllMenu(userName);
        //首先获得所有得到一级菜单
        List<SysMenuEntity> oneLevelMenuList =
                sysMenuEntities.stream()
                        .filter(sysMenuEntity -> sysMenuEntity.getParentId().intValue() == 0)
                        .collect(Collectors.toList());

        List<MenuVo> result = oneLevelMenuList.stream().map(
                oneLevelMenu -> {
                    MenuVo menuVo = new MenuVo();
                    BeanUtils.copyProperties(oneLevelMenu, menuVo);
                    //设置其孩子
                    menuVo.setChildren(this.getChildren(menuVo, sysMenuEntities));
                    return menuVo;
                }
        ).collect(Collectors.toList());
        return result;
    }


    /**
     * 为root菜单寻找其子菜单 并组装
     *
     * @param root 当前菜单
     * @param all  所有菜单
     * @return
     */
    private List<MenuVo> getChildren(MenuVo root, List<SysMenuEntity> all) {
        //找到了root所有的子菜单  再为子菜单找到其子菜单
        List<MenuVo> allMenuList = all.stream()
                //过滤出当前菜单的子菜单
                .filter(menu -> menu.getParentId().equals(root.getId()))
                //递归处理当前菜单的子菜单
                .map(children -> {
                    MenuVo menuVo = new MenuVo();
                    BeanUtils.copyProperties(children, menuVo);
                    menuVo.setChildren(this.getChildren(menuVo, all));
                    return menuVo;
                }).collect(Collectors.toList());
        return allMenuList;
    }

}
