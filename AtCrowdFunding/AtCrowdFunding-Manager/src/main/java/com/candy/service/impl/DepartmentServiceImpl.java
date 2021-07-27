package com.candy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.candy.bean.Department;
import com.candy.mapper.DepartmentMapper;
import com.candy.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Candy
 * @since 2021-07-07
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
