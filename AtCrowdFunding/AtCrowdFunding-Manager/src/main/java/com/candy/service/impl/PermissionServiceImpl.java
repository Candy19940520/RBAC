package com.candy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.candy.bean.Permission;
import com.candy.mapper.PermissionMapper;
import com.candy.service.PermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
