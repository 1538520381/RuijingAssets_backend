package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.AssetInquiryEntity;
import com.ruijing.assets.entity.vo.assetVO.AssetVoInHomePage;
import com.ruijing.assets.util.using.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 客户询价表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-27 00:09:01
 */
public interface AssetInquiryService extends IService<AssetInquiryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /*
     * @author: K0n9D1KuA
     * @description: 添加询价
     * @param: assetInquiryEntity 询价实体类
     * @date: 2023/1/29 1:00
     */
    void inquiry(AssetInquiryEntity assetInquiryEntity);

    /*
     * @author: K0n9D1KuA
     * @description:查询我的询价列表
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/1/29 1:00
     */
    List<AssetVoInHomePage> inquiryInfo();
}

