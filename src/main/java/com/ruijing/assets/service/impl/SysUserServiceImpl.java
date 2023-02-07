package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.SysUserDao;
import com.ruijing.assets.entity.dto.UpdatePasswordDTO;
import com.ruijing.assets.entity.dto.UserDTO;
import com.ruijing.assets.entity.pojo.SysRoleEntity;
import com.ruijing.assets.entity.pojo.SysUserEntity;
import com.ruijing.assets.entity.pojo.SysUserRoleEntity;
import com.ruijing.assets.entity.vo.userVO.UserInfoVo;
import com.ruijing.assets.entity.vo.userVO.UserVo;
import com.ruijing.assets.enume.status.UserStatus;
import com.ruijing.assets.service.SysRoleService;
import com.ruijing.assets.service.SysUserRoleService;
import com.ruijing.assets.service.SysUserService;
import com.ruijing.assets.util.using.IpageConvertUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import com.ruijing.assets.util.using.SpringSecurityUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    SysUserRoleService sysUserRoleService;


    @Autowired
    SysRoleService sysRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<SysUserEntity> lambaWrapper = new LambdaQueryWrapper<>();
        //按照用户名模糊查询
        if (params.get("key") != null) {
            String key = params.get("key").toString();
            if (!StringUtils.isEmpty(key)) {
                lambaWrapper.like(SysUserEntity::getUserName, key);
            }
        }
        IPage<SysUserEntity> source = this.page(
                new Query<SysUserEntity>().getPage(params),
                lambaWrapper
        );
        //类型转化
        List<UserInfoVo> targetList = source.getRecords()
                .stream()
                .map(sysUserEntity -> {
                    UserInfoVo userInfoVo = new UserInfoVo();
                    BeanUtils.copyProperties(sysUserEntity, userInfoVo);
                    //查询对应角色列表
                    List<String> userRoleName = this.getUserRoleName(userInfoVo.getUserName());
                    userInfoVo.setRoleNames(userRoleName);
                    return userInfoVo;
                })
                .collect(Collectors.toList());
        IPage<UserInfoVo> target = IpageConvertUtil.iPageS2T(source, targetList);
        return new PageUtils(target);
    }

    @Override
    public void addUser(UserDTO userDTO) {
        //插入User
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtils.copyProperties(userDTO, sysUserEntity);
        //创建用户都是正常使用
        sysUserEntity.setUserStatus(UserStatus.NORMAL_STATUS.getCode());
        //修改时间 创建时间
        sysUserEntity.setCreateTime(new Date());
        sysUserEntity.setUpdateTime(new Date());
        //需要对密码进行加密
        sysUserEntity.setPassword(passwordEncoder.encode(sysUserEntity.getPassword()));
        //插入
        this.save(sysUserEntity);
        userDTO.setId(sysUserEntity.getId());
        //绑定user和角色之间的关系
        if (!CollectionUtils.isEmpty(userDTO.getRoleIds())) {
            this.bindUserAndRole(userDTO);
        }
    }


    //给某个用户分配角色
    @Override
    public void deliverRole(UserDTO userDTO) {
        this.bindUserAndRole(userDTO);
    }

    //移除user和角色之间的关系
    @Override
    public void removeRole(UserDTO userDTO) {
        //移除user和角色之间的关系
        this.removeUserAndRole(userDTO);
    }

    @Override
    public void removeUser(Long userId) {
        //删除角色
        this.removeById(userId);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        //移除user和角色之间的关系
        this.removeUserAndRole(userDTO);
    }

    @Override
    public UserVo userInfo(Long userId) {
        //获得用户
        SysUserEntity sysUserEntity = this.getById(userId);
        UserVo result = new UserVo();
        BeanUtils.copyProperties(sysUserEntity, result);
        //查询该用户对应的角色
        List<SysUserRoleEntity> sysUserRoleEntityList = sysUserRoleService.list(
                new LambdaQueryWrapper<SysUserRoleEntity>()
                        .eq(SysUserRoleEntity::getSysUserId, userId)
        );
        if (!CollectionUtils.isEmpty(sysUserRoleEntityList)) {
            //收集他们的roleIds
            List<Long> roleIds =
                    sysUserRoleEntityList
                            .stream()
                            .map(sysUserRoleEntity -> sysUserRoleEntity.getSysRoleId())
                            .collect(Collectors.toList());
            //查询
            List<SysRoleEntity> sysRoleEntities =
                    sysRoleService.list(new LambdaQueryWrapper<SysRoleEntity>()
                            .in(SysRoleEntity::getId, roleIds)
                    );
            result.setRoles(sysRoleEntities);
        } else {
            result.setRoles(Collections.emptyList());
        }
        //返回结果
        return result;
    }

    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        //获取当前用户的id
        Long userId = SpringSecurityUtil.getUserId();
        //要修改的密码
        String password = updatePasswordDTO.getPassword();
        //密码加密
        String encodePassword = passwordEncoder.encode(password);
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(userId);
        sysUserEntity.setPassword(password);
        this.updateById(sysUserEntity);
    }

    //移除user和角色之间的关系
    private void removeUserAndRole(UserDTO userDTO) {
        LambdaQueryWrapper<SysUserRoleEntity> sysUserRoleEntityLambdaQueryWrapper
                = new LambdaQueryWrapper<>();
        sysUserRoleEntityLambdaQueryWrapper
                .eq(!userDTO.getId().equals(0), SysUserRoleEntity::getSysUserId, userDTO.getId())
                .in(!CollectionUtils.isEmpty(userDTO.getRoleIds()), SysUserRoleEntity::getSysRoleId, userDTO.getRoleIds());
        //删除
        sysUserRoleService.remove(sysUserRoleEntityLambdaQueryWrapper);
    }

    //绑定user和角色之间的关系
    private void bindUserAndRole(UserDTO userDTO) {
        List<SysUserRoleEntity> sysUserRoleEntityListToBeSaved = new ArrayList<>();
        userDTO.getRoleIds().forEach(roleId -> {
            SysUserRoleEntity sysUserRoleEntity = SysUserRoleEntity.builder()
                    .sysRoleId(roleId)
                    .sysUserId(userDTO.getId())
                    .build();
            sysUserRoleEntityListToBeSaved.add(sysUserRoleEntity);
        });
        //批量添加
        sysUserRoleService.saveBatch(sysUserRoleEntityListToBeSaved);
    }

    //根据访问者姓名查询其对应角色名称
    private List<String> getUserRoleName(String userName) {
        //首先获得用户id
        SysUserEntity sysUserServiceOne = this.getOne(new LambdaQueryWrapper<SysUserEntity>()
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
