package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.AboutUsCompanyDao;
import com.ruijing.assets.dao.AboutUsDao;
import com.ruijing.assets.entity.pojo.AboutUsCompanyEntity;
import com.ruijing.assets.entity.pojo.AboutUsEntity;
import com.ruijing.assets.service.AboutUsCompanyService;
import com.ruijing.assets.service.AboutUsService;
import org.springframework.stereotype.Service;


@Service
public class AboutUsCompanyServiceImpl extends ServiceImpl<AboutUsCompanyDao, AboutUsCompanyEntity> implements AboutUsCompanyService {
}
