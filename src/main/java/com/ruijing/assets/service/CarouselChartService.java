package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.pojo.CarouselChartEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.util.using.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 小程序上方轮播图
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
public interface CarouselChartService extends IService<CarouselChartEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /*
     * @author: K0n9D1KuA
     * @description: 获得所有轮播图
     * @date: 2022/12/24 16:21
     */

    R getAllCarouselChart();


    String uploadCarouselChart(byte[] bytes, String originalFilename, String contentType);

    void deleteByIds( List<Long> ids);
}

