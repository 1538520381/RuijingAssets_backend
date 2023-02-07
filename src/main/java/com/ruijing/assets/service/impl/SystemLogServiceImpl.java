package com.ruijing.assets.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.SysLogDao;
import com.ruijing.assets.entity.pojo.SysLogEntity;
import com.ruijing.assets.entity.event.SysLogEvent;
import com.ruijing.assets.entity.pojo.SysRoleEntity;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.entity.pojo.SysUserRoleEntity;
import com.ruijing.assets.entity.queryDTO.SysLogQueryDTO;
import com.ruijing.assets.entity.vo.logVO.SystemLogVo;
import com.ruijing.assets.service.SysLogService;
import com.ruijing.assets.service.SysRoleService;
import com.ruijing.assets.service.SysUserRoleService;
import com.ruijing.assets.service.SysUserService;
import com.ruijing.assets.util.using.IpageConvertUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SystemLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysUserService sysUserService;

    /**
     * 监听日志记录时间
     * 将日志同步到数据库中
     */
    @Async
    @EventListener(SysLogEvent.class)
    public void doEvent(SysLogEvent sysLogEvent) {
        log.info("副线程开始啦-----------");
        this.save(sysLogEvent.getSysLogEntity());
        log.info("副线程结束啦-----------");
    }


    /*
     * @author: K0n9D1KuA
     * @description: 日志列表
     * @param: params
     * 功能描述 ：
     * 1，按照用户名查（模糊） ->userName = ? userName是null 或者 "" 那么就不拼接
     * 2，按照时间升序 or 降序  -> timeOrder = 0(升序) 1(降序)
     * @return:
     * @return: com.ruijing.assets.util.using.PageUtils
     * @date: 2023/2/5 0:53
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, SysLogQueryDTO sysLogQueryDTO) {
        LambdaQueryWrapper<SysLogEntity> sysLogEntityLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        //获得userName
        String userName = sysLogQueryDTO.getUserName();
        Integer timeOrder = sysLogQueryDTO.getTimeOrder();
        if (!StringUtils.isBlank(userName)) {
            sysLogEntityLambdaQueryWrapper
                    //模糊查询
                    .like(SysLogEntity::getUserName, userName);
        }
        //时间升序or降序
        sysLogEntityLambdaQueryWrapper.orderBy(true, timeOrder.equals(1), SysLogEntity::getVisitTime);

        //查询
        IPage<SysLogEntity> source = this.page(
                new Query<SysLogEntity>().getPage(params),
                sysLogEntityLambdaQueryWrapper
        );

        //属性封装 sysLogEntity->SystemLogVo
        List<SysLogEntity> sysLogEntityList = source.getRecords();
        List<SystemLogVo> targetRecords = sysLogEntityList
                .stream()
                .map(this::sysLogEntity2SystemLogVo)
                .collect(Collectors.toList());

        //IPage<SysLogEntity> -> IPage<SystemLogVo>
        IPage<SystemLogVo> target = IpageConvertUtil.iPageS2T(source, targetRecords);
        return new PageUtils(target);
    }

    //属性封装 sysLogEntity->SystemLogVo
    private SystemLogVo sysLogEntity2SystemLogVo(SysLogEntity source) {
        SystemLogVo target = new SystemLogVo();
        BeanUtils.copyProperties(source, target);
        //查询角色名
        target.setRoleNames(getUserRoleName(source.getUserName()));
        return target;
    }


    //根据访问者姓名查询其对应角色名称
    private List<String> getUserRoleName(String userName) {
        //首先获得用户id
        SysUserEntity sysUserServiceOne = sysUserService.getOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getUserName, userName));
        Long sysUserId = sysUserServiceOne.getId();

        //根据用户id获得角色id
        List<SysUserRoleEntity> sysUserRoleEntityList = sysUserRoleService.list(
                new LambdaQueryWrapper<SysUserRoleEntity>()
                        .eq(SysUserRoleEntity::getSysUserId, sysUserId));

        //收集角色id
        List<Long> roleIds = sysUserRoleEntityList
                .stream()
                .map(sysUserRoleEntity -> sysUserRoleEntity.getSysRoleId())
                .collect(Collectors.toList());

        //获得角色id 获得角色实体类
        Collection<SysRoleEntity> sysRoleEntities = sysRoleService.listByIds(roleIds);

        //映射
        List<String> roleNames = sysRoleEntities.stream().map(
                sysRoleEntity -> sysRoleEntity.getName()
        ).collect(Collectors.toList());
        //返回角色名
        return roleNames;
    }


}
