package com.ruijing.assets.util.using;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author K0n9D1KuA
 * @version 1.0
 * @description: 封装Ipage
 * 1,从IPage<S> source 变为 IPage<T> target
 * @email 3161788646@qq.com
 * @date 2023/2/6 4:13
 */

public class IpageConvertUtil {
    //属性封装 从IPage<S> source 变为 IPage<T> target
    public static <S, T> IPage<T> iPageS2T(IPage<S> source, List<T> targetRecords) {
        IPage<T> target = new Page<>();
        BeanUtils.copyProperties(source, target, "records");
        target.setRecords(targetRecords);
        return target;
    }
}
