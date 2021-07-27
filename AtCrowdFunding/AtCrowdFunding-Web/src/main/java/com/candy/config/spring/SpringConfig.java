package com.candy.config.spring;

import com.candy.config.interceptor.RoleInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Candy
 * @create 2021-04-14 16:46
 * Spring父容器
 */
@Configuration
@ComponentScan(value = "com.candy",
excludeFilters = {
    @ComponentScan.Filter(
        type = FilterType.ANNOTATION,classes = Controller.class
    )
})//包扫描排除了Controller,为了和web容器，形成父子容器
@Import(JDBCconfig.class)
@EnableTransactionManagement(proxyTargetClass = true)//开启事务
@EnableAspectJAutoProxy(proxyTargetClass = true)//开启AOP功能，并且之前无论何时都使用CGLIB实现动态代理
public class SpringConfig {
    @Bean
    public HandlerInterceptor getRoleInterceptor(){
        return new RoleInterceptor();
    }
}
