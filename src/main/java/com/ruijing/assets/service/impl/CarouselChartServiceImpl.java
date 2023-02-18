package com.ruijing.assets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruijing.assets.dao.CarouselChartDao;
import com.ruijing.assets.entity.pojo.CarouselChartEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.enume.exception.RuiJingExceptionEnum;
import com.ruijing.assets.exception.RuiJingException;
import com.ruijing.assets.service.CarouselChartService;

import com.ruijing.assets.service.UploadService;
import com.ruijing.assets.util.using.MinioUtil;
import com.ruijing.assets.util.using.PageUtils;
import com.ruijing.assets.util.unUsing.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service("carouselChartService")
public class CarouselChartServiceImpl extends ServiceImpl<CarouselChartDao, CarouselChartEntity> implements CarouselChartService {

    @Autowired
    MinioUtil minioUtil;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CarouselChartEntity> page = this.page(new Query<CarouselChartEntity>().getPage(params), new QueryWrapper<CarouselChartEntity>());
        return new PageUtils(page);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 获得所有轮播图
     * @date: 2022/12/24 16:21
     */
    @Override
    public R getAllCarouselChart() {
        List<CarouselChartEntity> carouselChartEntities = this.list();
        if (carouselChartEntities == null || carouselChartEntities.size() == 0) {
            return R.success(Collections.emptyList());
        } else {
            carouselChartEntities
                    .stream()
                    //排序
                    .sorted(Comparator.comparingInt(CarouselChartEntity::getImageOrder))
                    //处理url
                    //ruijing/2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg
                    //变为http://175.178.189.129:9000/ruijing/2023/02/06/a730488ecbb28a73a98f2e5281db6fb1.jpeg
                    .forEach(
                            carouselChartEntity
                                    -> carouselChartEntity
                                    .setImage(minioUtil.getEndpoint() + "/" + carouselChartEntity.getImage()));

            return R.success(carouselChartEntities);
        }
    }

    @Override
    public String uploadCarouselChart(byte[] bytes, String originalFilename, String contentType) {
        //上传文件
        try {
            String originalUrl = minioUtil.uploadFile(bytes, originalFilename, contentType);
            //更新数据库
            CarouselChartEntity carouselChartEntity = new CarouselChartEntity();
            carouselChartEntity.setImage(originalUrl);
            //默认顺序是1
            carouselChartEntity.setImageOrder(1);
            //插入数据库
            this.save(carouselChartEntity);
            //返回最后的url
            return minioUtil.END_POINT + "/" + originalFilename;
        } catch (
                Exception e
        ) {
            //抛出异常 上传错误
            throw new RuiJingException(
                    RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getMsg()
                    , RuiJingExceptionEnum.UPLOAD_FILE_FAILED.getCode()
            );
        }


    }


    /*
     * @author: K0n9D1KuA
     * @description: 根据ids从minio文件系统中删除图片 并且从数据库中删除
     * @param: ids 待删除的轮播图的id集合
     * @date: 2023/2/7 0:47
     */

    @Override
    public void deleteByIds(List<Long> ids) {

        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(currentDeleteId -> {
                CarouselChartEntity carouselChartEntity = this.getById(currentDeleteId);
                //获得他的url
                //数据库中的url  ruijing/2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
                String imageUrl = carouselChartEntity.getImage();
                //将imageUrl转化为objectName
                //转化为   2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
                String objectName = this.convertImageUrlToObjectName(imageUrl);
                //删除
                minioUtil.deleteFileByObjectName(objectName);
                //同时删除数据库中的数据
                this.removeById(currentDeleteId);
            });
        }
    }

    //  根据url获得objectName
    //  ruijing/2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
    //  2023/02/06/2d7adc6c312190602c7e3d20efa200c7.png
    private String convertImageUrlToObjectName(String originalUrl) {
        //  ruijing/
        String cutSubString = minioUtil.getBucket() + "/";
        return originalUrl.substring(cutSubString.length());
    }


}
