package com.ruijing.assets.controller;

import com.ruijing.assets.annotation.SysLog;
import com.ruijing.assets.entity.dto.AssetInsertDTO;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.AssetService;

import com.ruijing.assets.util.using.PageUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Arrays;
import java.util.Map;


/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 房屋资产基本信息表
 * @email 3161788646@qq.com
 * @date 2022/12/15 0:55
 */

@RestController
@RequestMapping("assets/asset")
@Slf4j
public class AssetController {
    @Autowired
    private AssetService assetService;

    /*
     * @author: K0n9D1KuA
     * @description: 查询资产列表
     * @param: params  key 支持按照资产名字模糊查询
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:36
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = assetService.queryPage(params);
        return R.ok().put("page", page);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 根据资产id获取资产所有信息
     * @param: 资产id
     * @return: com.ruijing.assets.entity.result.R 结果
     * @date: 2022/12/16 15:07
     */
    @GetMapping("/assetInfo/{assetId}")

    public R getAssetInfo( @PathVariable Long assetId) {
        return assetService.getAssetInfo(assetId);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 添加债权信息
     * @param: assetInsertDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 19:21
     */
    @PostMapping("/addAsset")
    @SysLog(operationName = "新增债权", operationType = 2)
    public R addAsset(@RequestBody AssetInsertDTO assetInsertDTO) {
        assetService.addAsset(assetInsertDTO);
        return R.ok();
    }

    /*
     * @author: K0n9D1KuA
     * @description:
     * @param: assetId 资产id
     * @param: file
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 17:18
     */
    @GetMapping("upload/{assetId}")
    @SysLog(operationName = "上传资产图片", operationType = 2)
    public R upload(@PathVariable Long assetId, @RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            byte[] bytes = file.getBytes();
            assetService.upload(bytes, originalFilename, contentType, assetId);
            return R.ok();
        } catch (Exception e) {
            //上传文件失败
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }
    }


    /*
     * @author: K0n9D1KuA
     * @description:
     * @param: assetId 资产id
     * @param: file
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 17:18
     */
    @GetMapping("upload/{assetImageId}")
    @SysLog(operationName = "删除资产图片", operationType = 2)
    public R upload(@PathVariable Long assetImageId) {
        try {
            assetService.deleteImage(assetImageId);
            return R.ok();
        } catch (Exception e) {
            //删除文件失败
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.DELETE_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.DELETE_FILE_FAILED.getCode()
            );
        }
    }
}
