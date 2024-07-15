package com.ruijing.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruijing.assets.entity.dto.TraceDto;
import com.ruijing.assets.entity.dto.TraceInvestorAssetDto;
import com.ruijing.assets.entity.pojo.TraceEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/14 18:51
 */
public interface TraceService extends IService<TraceEntity> {
    List<TraceDto> getAllByUser(Long userId);

    List<TraceDto> get(Long investorId, Long assetId, Long userId);

    //    List<TraceInvestorAssetDto> getUnend(Long userId);
    List<TraceDto> getUnend(Long userId);

    Long addByUnique(TraceEntity traceEntity);

    List<TraceDto> match(Long assetId, Long userId);
}
