package com.candy.config.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.candy.bean.Permission;
import com.candy.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Candy
 * @create 2021-07-09 19:50
 */
public class RoleInterceptor implements HandlerInterceptor {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //1.获取当前得请求地址
        String url = request.getRequestURI();
        HttpSession session = request.getSession();
        //2.查询出所有需要认证得路径集合，判断当前路径是否需要权限认证
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("url");
        List<Permission> permissionList = permissionMapper.selectList(queryWrapper);
        Set<String> urls = new HashSet<>();
        for (Permission permission : permissionList) {
            if(StrUtil.isNotBlank(permission.getUrl())){
                urls.add(permission.getUrl());
            }
        }
        if(urls.contains(url)){
            //判断当前用户是否拥有对应得权限
            Set<String> authUrlSet = (Set<String>)session.getAttribute("authUrlSet");
            if(CollUtil.isNotEmpty(authUrlSet) && authUrlSet.contains(url)){
                return true;
            }else{
                String contextPath = session.getServletContext().getContextPath();//加上项目名称
                response.sendRedirect(contextPath+"/error");
                return false;
            }
        }else{
            return true;
        }
    }
}
