package com.ruijing.assets.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.ContactUsDao;
import com.ruijing.assets.entity.pojo.ContactUsEntity;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.ContactUsService;
import com.ruijing.assets.service.UploadService;
import com.ruijing.assets.util.using.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl extends ServiceImpl<ContactUsDao, ContactUsEntity> implements ContactUsService {
    @Autowired
    MinioUtil minioUtil;


    @Override
    public ContactUsEntity info() {
        ContactUsEntity contactUsEntity = this.list().get(0);
        //处理图片
        contactUsEntity.setImage(minioUtil.getEndpoint() + "/" + contactUsEntity.getImage());
        return contactUsEntity;
    }

    @Override
    public String uploadAndDeleteImage(byte[] bytes, String originalFilename, String contentType) {
        try {
            String originalUrl = minioUtil.uploadFile(bytes, originalFilename, contentType);
            //上传文件成功
            //同时从minio中删除图片
            ContactUsEntity contactUsEntity = this.info();
            //获得原来的图片
            String image = contactUsEntity.getImage();
            //转化成objectName
            String objectName = convertImageUrlToObjectName(image);
            //从minio中删除图片
            try {
                minioUtil.deleteFileByObjectName(objectName);
            } catch (Exception e) {
                //抛出异常 上传错误
                throw new RuiJingException(
                        RuiJingExceptionEnum.DELETE_FILE_FAILED.getMsg()
                        , RuiJingExceptionEnum.DELETE_FILE_FAILED.getCode()
                );
            }
            //以上都成功 更新数据库
            contactUsEntity.setImage(originalFilename);
            //返回结果
            return minioUtil.getEndpoint() + originalUrl;
        } catch (Exception e) {
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }
    }

    //  根据url获得objectName
    //  ruijing/2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
    //  2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
    private String convertImageUrlToObjectName(String originalUrl) {
        //  ruijing/
        String cutSubString = minioUtil.getBucket() + "/";
        return originalUrl.substring(cutSubString.length());
    }
}
