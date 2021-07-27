package com.candy.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Candy
 * @since 2021-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Employee extends Model<Employee> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "employee_id", type = IdType.AUTO)
    private Integer employeeId;

    private String lastName;

    private String email;

    private String gender;

    private Integer age;

    private Date createTime;

    private Date updateTime;

    /**
     * 是否删除-1删除  0未删除
     */
    private Integer isDelete;

    private Integer departmentId;


    @Override
    protected Serializable pkVal() {
        return this.employeeId;
    }

}
