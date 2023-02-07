package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.dto.AssetInsertDTO;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.util.using.PageUtils;

import java.util.Map;

/**
 * 房屋资产基本信息表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
public interface AssetService extends IService<AssetEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /*
     * @author: K0n9D1KuA
     * @description: 根据资产id获取资产所有信息
     * @param: 资产id
     * @return: com.ruijing.assets.entity.result.R 结果
     * @date: 2022/12/16 15:07
     */
    R getAssetInfo(Long assetId);


    void addAsset(AssetInsertDTO assetInsertDTO);

    String upload(byte[] bytes, String originalFilename, String contentType, Long assetId);

    void deleteImage(Long assetImageId);
}

