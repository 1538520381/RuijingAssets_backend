package com.ruijing.assets.entity.queryDTO;


import lombok.Data;

@Data
public class SysLogQueryDTO {
    //按照用户名查（模糊） ->userName = ? userName是null 或者 "" 那么就不拼接
    private String userName;
    //按照时间升序 or 降序  -> timeOrder = 0(升序) 1(降序)
    private Integer timeOrder;
}
