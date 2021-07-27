package com.candy.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.candy.bean.DepartmentAndPermission;
import com.candy.bean.Employee;
import com.candy.bean.Permission;
import com.candy.bean.mybean.PermissionTree;
import com.candy.mapper.DepartmentAndPermissionMapper;
import com.candy.mapper.PermissionMapper;
import com.candy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Candy
 * @create 2021-06-07 11:29
 */
@Controller
public class LoginController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentAndPermissionMapper departmentAndPermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @RequestMapping("/doAJAXLogin")
    @ResponseBody
    public Object doAJAXLogin(Employee employee, HttpSession httpSession){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",false);
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("email",employee.getEmail());
        employeeQueryWrapper.eq("last_name",employee.getLastName());
        List<Employee> list = employeeService.list(employeeQueryWrapper);
        if(list.size() == 1){
            Employee employee1 = list.get(0);
            jsonObject.put("success",true);
            httpSession.setAttribute("loginUser",employee1);
            //权限赋值
            Integer departmentId = employee1.getDepartmentId();
            QueryWrapper<DepartmentAndPermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("department_id",departmentId);
            List<DepartmentAndPermission> departmentAndPermissions = departmentAndPermissionMapper.selectList(queryWrapper);
            List<Long> permissionIds = new ArrayList<>();//菜单id集合
            for (DepartmentAndPermission departmentAndPermission : departmentAndPermissions) {
                permissionIds.add(departmentAndPermission.getPermissionId());
            }
            PermissionTree permissionTree = new PermissionTree();
            if(CollUtil.isNotEmpty(permissionIds)){
                List<Permission> permissionList = permissionMapper.selectBatchIds(permissionIds);
                Set<String> authUrlSet = new HashSet<>();//存入授权得url集合
                for (Permission permission : permissionList) {
                    authUrlSet.add(permission.getUrl());
                }
                httpSession.setAttribute("authUrlSet",authUrlSet);
                List<PermissionTree> parentIdPermissionsNew = getParentIdPermissionsNew(permissionList);
                permissionTree = parentIdPermissionsNew.get(0);
            }
            httpSession.setAttribute("rootPermission",permissionTree);
        }
        return jsonObject;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/error")
    public String error(){
        return "error";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession httpSession){
//        httpSession.removeAttribute("loginUser");
        httpSession.invalidate();//清空session所有数据
        return "redirect:login";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/doLogin")
    public String doLogin(Employee employee, Model model) throws Exception {
        /**
         * 1.传递数据的时候，会采用当前浏览器的编码方式传递数据(当前页面编码方式是UTF-8)
         * 2.服务器默认使用IOS8859-1编码方式解析数据，所以我们接收到的数据是乱码
         */
        //解决方案一：
//        byte[] bytes = employee.getEmail().getBytes("ISO8859-1");
//        employee.setEmail(new String(bytes,"UTF-8"));
        //解决方案二：使用过滤器

        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("email",employee.getEmail());
        employeeQueryWrapper.eq("last_name",employee.getLastName());
        List<Employee> list = employeeService.list(employeeQueryWrapper);
        if(list.size() == 1){
            return "main";
        }
        model.addAttribute("errorMsg","账号或密码错误，请重新输入");
        return "redirect:login";
    }

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
