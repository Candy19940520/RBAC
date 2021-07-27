package com.candy.config;


import com.candy.config.spring.SpringConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
/**
 * 用来代替web.xml配置文件的作用
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //获取父容器的配置类(Spring配置文件)
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringConfig.class};
    }

    //获取web子容器的配置类(SpringMVC配置文件)
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{SpringMVCConfig.class};
    }

    /**
     * 配置DispatcherServlet请求映射信息:
     * /：拦截所有请求，包括静态资源，但是不包括*.jsp
     * /*：拦截所有请求，包括静态资源，还包括*.jsp
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //支持REST请求方式和解决乱码
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {new CharacterEncodingFilter("UTF-8"),new HiddenHttpMethodFilter()};
    }
    //文件上传
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement("webapp/upload",10485760L,-1L,0));
    }


}
