package com.ruijing.assets.entity.vo;


import com.ruijing.assets.entity.pojo.AboutUsCompanyEntity;
import com.ruijing.assets.entity.pojo.AboutUsEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AboutUsVo {
    private AboutUsEntity aboutUsEntity;
    private List<AboutUsCompanyEntity> aboutUsCompanyEntityList;
}
