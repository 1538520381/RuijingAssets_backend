package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.dto.AboutUsUpdateDto;
import com.ruijing.assets.entity.pojo.AboutUsEntity;
import com.ruijing.assets.entity.vo.aboutUsVO.AboutUsVo;

public interface AboutUsService extends IService<AboutUsEntity> {
    AboutUsVo getList();

    void updateInfo(AboutUsUpdateDto aboutUsUpdateDto);
}
