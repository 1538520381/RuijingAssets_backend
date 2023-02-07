package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.dto.AboutUsDto;
import com.ruijing.assets.entity.pojo.AboutUsEntity;
import com.ruijing.assets.entity.vo.AboutUsVo;

public interface AboutUsService extends IService<AboutUsEntity> {
    AboutUsVo getList();

    void updateInfo(AboutUsDto aboutUsDto);
}
