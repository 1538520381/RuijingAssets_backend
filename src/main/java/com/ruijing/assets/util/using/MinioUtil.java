package com.ruijing.assets.util.using;


import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


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

    public static MinioClient getMinioClient() {
        return minioClient;
    }
}
