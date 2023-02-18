package com.ruijing.assets;

import com.ruijing.assets.util.using.MinioUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;

@SpringBootTest
class RuijingAssetsAdminApplicationTests {


//    @Autowired
//    MinioUtil minioUtil;
////    static MinioClient minioClient =
////            MinioClient.builder()
////                    .endpoint("http://175.178.189.129:9000")
////                    .credentials("admin", "admin123")
////                    .build();
//
//    //测试文件上传是否成功
//    @Test
//    public void upload() {
//        MinioClient minioClient = minioUtil.minioClient;
//        UploadObjectArgs uploadObjectArgs = null;
//        try {
//            uploadObjectArgs = UploadObjectArgs.builder()
//                    .bucket("ruijing")
//                    .object("test2")//同一个桶内对象名不能重复
//                    .filename("D:\\excel\\excel.xls")
//                    .build();
//            //上传
//            minioClient.uploadObject(uploadObjectArgs);
//            System.out.println("上传成功了");
//        } catch (Exception e) {
//            System.out.println("上传文件失败了");
//        }
//    }
//
//
//    //删除文件
//    @Test
//    public void delete() {
//        MinioClient minioClient = minioUtil.minioClient;
//        try {
//            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs
//                    .builder()
//                    .bucket("ruijing")
//                    .object("2023/02/06/7b807a09d43431290174cc77df919fb0.png")
//                    .build();
//            minioClient.removeObject(removeObjectArgs);
//            System.out.println("删除文件成功");
//        } catch (Exception e) {
//            System.out.println("删除文件失败");
//        }
//
//    }
//
//    //查询文件
//    //如果文件存在 将文件下载
//    @Test
//    public void getFile() {
//        MinioClient minioClient = minioUtil.minioClient;
//        GetObjectArgs getObjectArgs = GetObjectArgs
//                .builder()
//                .bucket("ruijing")
//                .object("test")
//                .build();
//        try (
//                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
//                FileOutputStream outputStream = new FileOutputStream(new File("D:\\excel\\excel_s.xls"));
//        ) {
//            if (inputStream != null) {
//                IOUtils.copy(inputStream, outputStream);
//            }
//        } catch (Exception e) {
//        }
//
//    }
//
//    @Test
//    public void testSubstring() {
//        String url = "http://175.178.189.129:9000/ruijing/2023/02/06/8ecd6cd06811cb58e202ebad3bf2445d.jpeg";
//        //http://175.178.189.129:9000/ruijing/2023/02/06/8ecd6cd06811cb58e202ebad3bf2445d.jpeg
//        //变为 2023/02/06/0f08d09a6a5dee75a607857cc163aa9e.jpg
//        //测试截取掉 http://175.178.189.129:9000/ruijing/
//        String curSubString = minioUtil.getEndpoint() + "/" + minioUtil.getBucket() + "/";
//        String remainingString = url.substring(curSubString.length());
//        System.out.println(remainingString);
//    }
}
