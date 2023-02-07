package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AboutUsDao;
import com.ruijing.assets.entity.dto.AboutUsDto;
import com.ruijing.assets.entity.pojo.AboutUsCompanyEntity;
import com.ruijing.assets.entity.pojo.AboutUsEntity;
import com.ruijing.assets.entity.vo.aboutUsVO.AboutUsVo;
import com.ruijing.assets.service.AboutUsCompanyService;
import com.ruijing.assets.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class AboutUsServiceImpl extends ServiceImpl<AboutUsDao, AboutUsEntity> implements AboutUsService {
    @Autowired
    AboutUsCompanyService aboutUsCompanyService;

    @Override
    public AboutUsVo getList() {
        //基本信息
        AboutUsEntity aboutUsEntity = this.list().get(0);
        //公司信息
        List<AboutUsCompanyEntity> aboutUsCompanyEntityList = aboutUsCompanyService.list();
        //封装 返回
        return AboutUsVo.builder()
                .aboutUsEntity(aboutUsEntity)
                .aboutUsCompanyEntityList(aboutUsCompanyEntityList)
                .build();
    }

    @Override
    public void updateInfo(AboutUsDto aboutUsDto) {
        AboutUsEntity aboutUsEntity = aboutUsDto.getAboutUsEntity();
        this.updateById(aboutUsEntity);
        List<AboutUsCompanyEntity> aboutUsCompanyEntityList = aboutUsDto.getAboutUsCompanyEntityList();
        //遍历修改
        if (!CollectionUtils.isEmpty(aboutUsCompanyEntityList)) {
            aboutUsCompanyEntityList.forEach(aboutUsCompanyEntity
                    -> aboutUsCompanyService.updateById(aboutUsCompanyEntity
            ));
        }
    }
}
