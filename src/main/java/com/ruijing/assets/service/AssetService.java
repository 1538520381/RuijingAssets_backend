package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.dto.AssetAddDTO;
import com.ruijing.assets.entity.dto.AssetUpdateDTO;
import com.ruijing.assets.entity.pojo.AssetEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.util.using.PageUtils;

import java.util.List;
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


    /*
     * @author: K0n9D1KuA
     * @description: 添加债权信息
     * @param: assetAddDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/5 19:21
     */
    void addAsset(AssetAddDTO assetAddDTO);

    String upload(byte[] bytes, String originalFilename, String contentType, Long assetId);


    /*
     * @author: K0n9D1KuA
     * @description: 删除资产图片
     * @param: assetImageId 要删除的资产图片id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 17:18
     */
    void deleteImage(Long assetImageId);

    /*
     * @author: K0n9D1KuA
     * @description:
     * @param: 删除资产
     * @param: assetId 资产id
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 19:43
     */
    void deleteAsset(Long assetId);

    /*
     * @author: K0n9D1KuA
     * @description: 修改债权信息
     * @param: assetUpdateDTO
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/21 22:19
     */
    void updateAsset(AssetUpdateDTO assetUpdateDTO);

    /*
     * @author Persolute
     * @version 1.0
     * @description 债权匹配
     * @email 1538520381@qq.com
     * @date 2024/5/23 下午9:20
     */
    List<AssetEntity> match(String intentionRegion, List<Integer> investmentType, List<Integer> investmentAmount);
}

