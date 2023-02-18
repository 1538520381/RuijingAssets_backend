package com.ruijing.assets.controller;


import com.ruijing.assets.entity.pojo.ContactUsEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/assets/contact_us")
public class ContactUsController {


    @Autowired
    ContactUsService contactUsService;

    @GetMapping("/info")
    public R getInfo() {
        ContactUsEntity contactUsEntity = contactUsService.info();
        return R.success(contactUsEntity);
    }

    //修改
    @PostMapping("/update")
    public R update(@RequestBody ContactUsEntity contactUsEntity) {
        //修改
        contactUsService.updateById(contactUsEntity);
        return R.ok();
    }

    //修改简介图
    @RequestMapping("/updateImage")
    public R updateImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            //获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            //获得文件的类型
            String contentType = file.getContentType();
            String url = contactUsService.uploadAndDeleteImage(bytes, originalFilename, contentType);
            return R.success(url);
        } catch (Exception e) {
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }

    }

}
