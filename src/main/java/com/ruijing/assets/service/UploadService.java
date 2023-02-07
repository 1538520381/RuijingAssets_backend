package com.ruijing.assets.service;

import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UploadService {
    //删除文件
    void deleteFileByObjectName(String deleteObjectNames);

    String uploadFile(byte[] bytes, String originalFileName, String fileContent) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
