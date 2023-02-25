package com.ruijing.assets.controller;

import com.ruijing.assets.annotation.SysLog;
import com.ruijing.assets.entity.pojo.CarouselChartEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.CarouselChartService;

import com.ruijing.assets.service.UploadService;
import com.ruijing.assets.util.using.PageUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 小程序上方轮播图
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@RestController
@RequestMapping("assets/carouselchart")
public class CarouselChartController {

    @Autowired
    private CarouselChartService carouselChartService;


    /*
     * @author: K0n9D1KuA
     * @description: 删除轮播图
     * @param: ids 要删除的轮播图的id集合
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 0:46
     */
    @PostMapping("/delete")
    @SysLog(operationType = 2, operationName = "删除轮播图")
    public R delete(@RequestBody List<Long> ids) {
        try {
            carouselChartService.deleteByIds(ids);
            return R.ok();
        } catch (Exception e) {
            //删除文件失败 抛出异常
            throw new RuiJingException(RuiJingExceptionEnum.DELETE_FILE_FAILED.getMsg(),
                    RuiJingExceptionEnum.DELETE_FILE_FAILED.getCode());
        }
    }


    /*
     * @author: K0n9D1KuA
     * @description: 查看所有的轮播图
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 16:40
     */
    @GetMapping("/allCarouselChart")
    public R getAllCarouselChart() {
        return carouselChartService.getAllCarouselChart();
    }



    /*
     * @author: K0n9D1KuA
     * @description: 上传轮播图
     * @param: file
     * @return: com.ruijing.assets.entity.result.R 轮播图url
     * @date: 2023/2/7 16:39
     */

    @RequestMapping("/uploadCarouselChart")
//    @SysLog(operationType = 2, operationName = "添加轮播图")
    public R uploadCarouselChart(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            //获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            //获得文件的类型
            String contentType = file.getContentType();
            String url = carouselChartService.uploadCarouselChart(bytes, originalFilename, contentType);
            return R.success(url);
        } catch (Exception e) {
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }
    }
}
