package com.candy.config.interceptor;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录拦截器
 * @author Candy
 * @create 2021-07-09 19:24
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * Controller执行之前执行得方法preHandle；如果返回false则不会进入到Controller中
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if(ObjectUtil.isNull(loginUser)){
            String contextPath = session.getServletContext().getContextPath();//加上项目名称
            response.sendRedirect(contextPath+"/login");
            return false;
        }
        return true;
    }

    /**
     * Controller执行完毕之后执行此方法preHandle；
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 完成视图渲染之后，执行此方法afterCompletion
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
