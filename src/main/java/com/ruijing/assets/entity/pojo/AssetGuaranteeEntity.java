package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 房屋债权保证人
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@Data
//@Builder
@TableName("asset_guarantee")
public class AssetGuaranteeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 担保人名字
	 */
	private String guaranteeName;
	/**
	 * 担保方式
	 */
	private String method;
	/**
	 * 资产id
	 */
	private Long assetId;

}
