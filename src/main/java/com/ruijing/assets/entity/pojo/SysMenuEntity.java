package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统菜单
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:42
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单标题
	 */
	private String title;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 组件路径
	 */
	private String component;
	/**
	 * 路由地址
	 */
	private String path;
	/**
	 * 父id[如果是一级菜单，那么parent_id就是0]
	 */
	private Long parentId;

}
