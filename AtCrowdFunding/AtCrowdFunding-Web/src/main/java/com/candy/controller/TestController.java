package com.candy.controller;

import cn.hutool.json.JSONObject;
import com.candy.bean.Employee;
import com.candy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Candy
 * @create 2021-06-04 19:39
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public Object queryAll(){
        List<Employee> employeeList = employeeService.queryAll();
        return employeeList;
    }

    @RequestMapping("/index")
    public String index(){
        return "error";
    }

    @RequestMapping("/indexJSON")
    @ResponseBody
    public Object indexJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","孙健");
        jsonObject.put("age","30");
        return jsonObject;
    }

}
