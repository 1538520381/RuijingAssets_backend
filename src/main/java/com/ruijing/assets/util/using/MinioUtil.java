package com.ruijing.assets.util.using;


import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: MINIO工具类
 * 1，属性绑定
 * 2，获得minio客户端
 * @email 3161788646@qq.com
 * @date 2023/2/6 4:14
 */

@Component
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioUtil implements InitializingBean {

    public static MinioClient minioClient = null;
    public static String BUCKET;
    public static String END_POINT;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化minioClient
        BUCKET = this.bucket;
        END_POINT = this.endpoint;
        minioClient =
                MinioClient
                        .builder()
                        .endpoint(this.endpoint)
                        .credentials(this.accessKey, this.secretKey)
                        .build();
    }

    //删除minio系统中的文件
    public static void deleteFileByObjectName(String deleteObjectName) {
        try {
            //模拟异常
//            int i = 1 / 0;
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs
                    .builder()
                    .bucket(BUCKET)
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

    //将文件上传至minio系统
    public static String uploadFile(byte[] bytes, String originalFileName, String contentType) {
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
        String bucket = BUCKET;
        //     minio客户端
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

    //根据日期拼接目录
    private static String getFileFolder(Date date, boolean year, boolean month, boolean day) {
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


    public static MinioClient getMinioClient() {
        return minioClient;
    }

    //处理图片url
    // ruijing/2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg 变为
    // http://175.178.189.129:9000/ruijing/2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg
    public static String processImage(String originImage) {
        return END_POINT + "/" + originImage;
    }
}
