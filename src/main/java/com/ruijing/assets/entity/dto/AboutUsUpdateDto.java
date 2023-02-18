package com.ruijing.assets.entity.dto;

import com.ruijing.assets.entity.pojo.AboutUsCompanyEntity;
import lombok.Data;

import java.util.List;


@Data
public class AboutUsUpdateDto {
    private Long id;
    private String fax;
    private String email;
    private String phone;
    private List<AboutUsCompanyEntity> company;
}
