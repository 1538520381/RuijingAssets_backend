package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 每个资产浏览量与收藏量
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2022-12-15 00:46:41
 */
@Data
@TableName("asset_collection_browse")
public class AssetCollectionBrowseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 资产id
	 */
	private Long assetId;
	/**
	 * 收藏量
	 */
	private Long collection;
	/**
	 * 浏览量
	 */
	private Long browse;

}
