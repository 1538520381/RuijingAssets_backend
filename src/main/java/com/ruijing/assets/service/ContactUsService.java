package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.ContactUsEntity;

public interface ContactUsService extends IService<ContactUsEntity> {
    ContactUsEntity info();

    String uploadAndDeleteImage(byte[] bytes, String originalFilename, String contentType);
}
