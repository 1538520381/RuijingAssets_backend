package com.ruijing.assets.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruijing.assets.entity.pojo.TraceEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/14 19:19
 */
@Data
public class TraceDto {
    private Long id;

    private Long investorId;

    private String investorName;

    private Long assetId;

    private String assetUserName;

    private String assetName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate date;

    private Long userId;

    private String description;

    private Integer type;

    private String userName;

    private String createAssetName;

    private String createInvestorName;
}
