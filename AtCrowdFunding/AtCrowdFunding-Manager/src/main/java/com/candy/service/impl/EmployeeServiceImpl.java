package com.candy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.candy.bean.Employee;
import com.candy.mapper.EmployeeMapper;
import com.candy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Candy
 * @since 2021-06-07
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public List<Employee> queryAll() {
        return employeeMapper.selectList(null);
    }
}
