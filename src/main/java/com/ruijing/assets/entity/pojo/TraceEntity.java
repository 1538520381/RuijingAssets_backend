package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/14 18:49
 */
@Data
@TableName("trace")
public class TraceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Long investorId;

    private Long assetId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate date;

    private Long userId;

    private String description;

    private Integer type;
}
