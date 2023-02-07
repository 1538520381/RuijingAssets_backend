package com.ruijing.assets.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色表
 *
 * @author K0n9D1KuA
 * @email 3161788646@qq.com
 * @date 2023-01-31 14:41:43
 */
@Data
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 修改时间
	 */
	private Date updatedTime;
	/**
	 * 状态，[0，禁止  1，启用]
	 */
	private Integer roleStatus;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 唯一标识
	 */
	private String code;
	/**
	 * 角色相关备注
	 */
	private String remark;

}
