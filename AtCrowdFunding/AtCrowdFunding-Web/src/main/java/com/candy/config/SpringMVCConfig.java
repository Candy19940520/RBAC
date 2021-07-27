package com.candy.config;

import com.candy.config.interceptor.LoginInterceptor;
import com.candy.config.interceptor.RoleInterceptor;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Candy
 * @create 2021-04-14 16:46
 * web子容器
 * ****useDefaultFilters = false一定要写，禁用默认过滤规则
 */
@EnableWebMvc
@ComponentScan(value = "com.candy",useDefaultFilters = false,
includeFilters = {
    @ComponentScan.Filter(
        type = FilterType.ANNOTATION,classes = Controller.class
    )
})//包扫描只包含Controller,为了和web容器，形成父子容器
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    //配置视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    //静态资源访问
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //如果再拦截中有注入业务层接口，则需要以此方式
    @Bean
    public HandlerInterceptor getRoleInterceptor(){
        return new RoleInterceptor();
    }
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRoleInterceptor()).addPathPatterns("/**").excludePathPatterns("/doAJAXLogin","/login");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/doAJAXLogin","/login");
    }

//    //自定义类型转换器
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new MyConversionServiceFactroyBean());
//    }




}
