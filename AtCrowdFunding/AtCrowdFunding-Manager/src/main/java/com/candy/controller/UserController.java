package com.candy.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.candy.bean.Employee;
import com.candy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author Candy
 * @create 2021-06-22 12:15
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("deleteAllAJAX")
    @ResponseBody
    public Object deleteAllAJAX(Integer[] employeeIds){
        JSONObject jsonObject = new JSONObject();
        try {
            employeeService.removeByIds(Arrays.asList(employeeIds));
            jsonObject.put("success",true);
        }catch (Exception e){
            jsonObject.put("success",false);
            e.printStackTrace();
        }
        return jsonObject;
    }


    @RequestMapping("deleteAJAX")
    @ResponseBody
    public Object deleteAJAX(Integer employeeId){
        JSONObject jsonObject = new JSONObject();
        try {
            employeeService.removeById(employeeId);
            jsonObject.put("success",true);
        }catch (Exception e){
            jsonObject.put("success",false);
            e.printStackTrace();
        }
        return jsonObject;
    }

    @RequestMapping("update")
    public String update(Integer employeeId,Model model){
        Employee employee = employeeService.getById(employeeId);
        model.addAttribute("employee",employee);
        return "user/update";
    }

    @RequestMapping("updateAJAX")
    @ResponseBody
    public Object updateAJAX(Employee employee){
        JSONObject jsonObject = new JSONObject();
        try {
            employee.setUpdateTime(new Date());
            employeeService.updateById(employee);
            jsonObject.put("success",true);
        }catch (Exception e){
            jsonObject.put("success",false);
            e.printStackTrace();
        }
        return jsonObject;
    }

    @RequestMapping("add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("addAJAX")
    @ResponseBody
    public Object addAJAX(Employee employee){
        JSONObject jsonObject = new JSONObject();
        try {
            employee.setCreateTime(new Date());
            employee.setGender("1");
            employeeService.save(employee);
            jsonObject.put("success",true);
        }catch (Exception e){
            jsonObject.put("success",false);
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 用户首页-JSON
     * @return
     */
    @RequestMapping("indexView")
    public String indexView(){
        return "user/indexView";
    }

    @RequestMapping("pageQuery")
    @ResponseBody
    public Object pageQuery(@RequestParam(name = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                            @RequestParam(name = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                            @RequestParam(name = "lastName",required = false,defaultValue = "") String lastName){
        JSONObject jsonObject = new JSONObject();
        Page<Employee> page = null;
        long totalPage = 0;
        try {
            QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
            if(StrUtil.isNotBlank(lastName)){
                employeeQueryWrapper.like("last_name",lastName);
            }
            employeeQueryWrapper.orderByDesc("update_time");
            page = employeeService.page(new Page<>(pageNo, pageSize),employeeQueryWrapper);
            totalPage = page.getPages();
            jsonObject.put("success",true);
            jsonObject.put("data",page);
            jsonObject.put("totalPage",totalPage);
        } catch (JSONException e) {
            jsonObject.put("success",false);
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * 用户首页-弃用
     * @return
     */
    @RequestMapping("index")
    public String index(@RequestParam(name = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                        @RequestParam(name = "pageNo",required = false,defaultValue = "1") Integer pageNo, Model model){
        Page<Employee> page = employeeService.page(new Page<>(pageNo, pageSize));
        long totalPage = page.getPages();
        model.addAttribute("page",page);
        model.addAttribute("totalPage",totalPage);
        return "user/index";
    }


}
