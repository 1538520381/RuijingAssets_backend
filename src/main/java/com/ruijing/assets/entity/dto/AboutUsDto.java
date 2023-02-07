package com.ruijing.assets.entity.dto;

import com.ruijing.assets.entity.pojo.AboutUsCompanyEntity;
import com.ruijing.assets.entity.pojo.AboutUsEntity;
import lombok.Data;

import java.util.List;


@Data
public class AboutUsDto {
    private AboutUsEntity aboutUsEntity;
    private List<AboutUsCompanyEntity> aboutUsCompanyEntityList;
}
