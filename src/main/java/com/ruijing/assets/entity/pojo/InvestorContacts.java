package com.ruijing.assets.entity.pojo;

import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/08/05 20:39
 */
@Data
public class InvestorContacts {
    private Long id;

    private Long investorId;

    private String contacts;

    private String phone;
}
