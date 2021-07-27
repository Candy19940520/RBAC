package com.candy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.candy.bean.Employee;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Candy
 * @since 2021-06-07
 */
public interface EmployeeService extends IService<Employee> {

    List<Employee> queryAll();

}
