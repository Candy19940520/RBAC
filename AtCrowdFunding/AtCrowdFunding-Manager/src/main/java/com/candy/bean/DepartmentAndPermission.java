package com.candy.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门菜单关联表
 * </p>
 *
 * @author Candy
 * @since 2021-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentAndPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "department_and_permission_id", type = IdType.AUTO)
    private Long departmentAndPermissionId;

    /**
     * 部门编号id
     */
    private Long departmentId;

    /**
     * 菜单编号id
     */
    private Long permissionId;

    /**
     * 逻辑删除（0：否 1：是）
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;


}
