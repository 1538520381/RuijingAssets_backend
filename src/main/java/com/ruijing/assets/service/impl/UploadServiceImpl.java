package com.ruijing.assets.service.impl;

import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.UploadService;
import com.ruijing.assets.util.using.MinioUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    MinioUtil minioUtil;

    //上传文件
    @Override
    public String uploadFile(byte[] bytes, String originalFileName, String contentType) {
        //     得到文件的md5值
        //     7b807a09d43431290174cc77df919fb0
        String fileMd5 = DigestUtils.md5Hex(bytes);
        //     自动生成目录的路径 按年月日生成，
        //     /2023/02/06
        String folder = getFileFolder(new Date(), true, true, true);

        //     文件名称
        //     7b807a09d43431290174cc77df919fb0 + .png = 7b807a09d43431290174cc77df919fb0.png
        String objectName = fileMd5 + originalFileName.substring(originalFileName.lastIndexOf("."));
        //     再叠加上目录
        //     /2023/02/06/7b807a09d43431290174cc77df919fb0.png
        objectName = folder + objectName;
        //     ruijing
        String bucket = minioUtil.BUCKET;
        //     minio客户端
        MinioClient minioClient = minioUtil.getMinioClient();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                //InputStream stream, long objectSize 对象大小, long partSize 分片大小(-1表示5M,最大不要超过5T，最多10000)
                .stream(byteArrayInputStream, byteArrayInputStream.available(), -1)
                .contentType(contentType)
                .build();
        //上传到minio
        try {
            minioClient.putObject(putObjectArgs);
            //  返回原始url
            //  最后图片的url
            //  ruijing/2023/02/06/7b807a09d43431290174cc77df919fb0.png
            return bucket + "/" + objectName;
        } catch (Exception e) {
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }

    }

    //删除minio系统中的文件
    public void deleteFileByObjectName(String deleteObjectName) {
        MinioClient minioClient = minioUtil.getMinioClient();
        try {
            //模拟异常
//            int i = 1 / 0;
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs
                    .builder()
                    .bucket(minioUtil.getBucket())
                    .object(deleteObjectName)
                    .build();
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            //抛出异常 文件删除失败
            throw new RuiJingException(
                    RuiJingExceptionEnum.DELETE_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.DELETE_FILE_FAILED.getCode()
            );
        }
    }

    //根据日期拼接目录
    private String getFileFolder(Date date, boolean year, boolean month, boolean day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期字符串
        String dateString = sdf.format(new Date());
        //取出年、月、日
        String[] dateStringArray = dateString.split("-");
        StringBuffer folderString = new StringBuffer();
        if (year) {
            folderString.append(dateStringArray[0]);
            folderString.append("/");
        }
        if (month) {
            folderString.append(dateStringArray[1]);
            folderString.append("/");
        }
        if (day) {
            folderString.append(dateStringArray[2]);
            folderString.append("/");
        }
        return folderString.toString();
    }
}
