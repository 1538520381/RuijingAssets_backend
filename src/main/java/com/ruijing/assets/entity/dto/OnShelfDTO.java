package com.ruijing.assets.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OnShelfDTO {

    //主键
    private Long id;


    //下架时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

}
