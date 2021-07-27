package com.candy.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.candy.bean.Permission;
import com.candy.bean.mybean.PermissionTree;
import com.candy.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Candy
 * @create 2021-07-07 11:09
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionMapper permissionMapper;

    @RequestMapping("permissionIndex")
    public String indexView(){
        return "permission/permissionIndex";
    }

    @RequestMapping("permissionIndexAjax")
    @ResponseBody
    public Object permissionIndexAjax(){
        List<Permission> permissionList = permissionMapper.selectList(null);//所有数据
        return getParentIdPermissionsNew(permissionList);
    }

    /**
     * 递归查询菜单,传入顶级菜单即可
     * 优化1：可以将其递归关系放入缓存中；在对菜单有新增，修改，删除操作的时候，修改其缓存值即可
     * 优化2：如果缓存有失效时间，可以做定时任务、或者项目启动的时候加载一次即可
     * 优化3：因为递归查询效率太低，可以将数据全部提取出来，在内存中进行递归操作
     */
    private List<PermissionTree> getParentIdPermissions(Long parentId){
        List<PermissionTree> permissionTreeList = new ArrayList<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<Permission> permissionList = permissionMapper.selectList(queryWrapper);
        if(CollUtil.isNotEmpty(permissionList)){
            for (Permission permission : permissionList) {
                PermissionTree permissionTree = new PermissionTree();
                permissionTree.setOpen(true);
                BeanUtil.copyProperties(permission,permissionTree);
                permissionTree.setChildren(getParentIdPermissions(permission.getPermissionId()));
                permissionTreeList.add(permissionTree);
            }
        }
        return permissionTreeList;
    }

    //优化3的实现方案
    private List<PermissionTree> getParentIdPermissionsNew(List<Permission> permissionList){
        List<PermissionTree> permissionTrees = new ArrayList<>();//菜单集合

        List<PermissionTree> permissionTreeList = new ArrayList<>();
        for (Permission permission : permissionList) {
            PermissionTree permissionTree = new PermissionTree();
            permissionTree.setOpen(true);
            BeanUtil.copyProperties(permission,permissionTree);
            permissionTreeList.add(permissionTree);
        }
        Map<Long,PermissionTree> permissionTreeMap = new HashMap<>();
        for (PermissionTree permissionTree : permissionTreeList) {
            permissionTreeMap.put(permissionTree.getPermissionId(),permissionTree);
        }
        for (PermissionTree permissionTree : permissionTreeList) {
            if(permissionTree.getParentId().equals(0L)){
                permissionTrees.add(permissionTree);
            }else{
                PermissionTree parent = permissionTreeMap.get(permissionTree.getParentId());
                parent.getChildren().add(permissionTree);
            }
        }
        return permissionTrees;
    }

}
