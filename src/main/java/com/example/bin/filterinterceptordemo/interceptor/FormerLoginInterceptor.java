package com.example.bin.filterinterceptordemo.interceptor;

import com.example.bin.filterinterceptordemo.service.UserPrivilegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormerLoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FormerLoginInterceptor.class);


    @Autowired
    UserPrivilegeService privilegeService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("~~~~~~~~~~~~~~~~~~~~ FormerLoginInterceptor preHandle ~~~~~~~~~~~~~~~~~~~~");

        logger.info("Request URL: " + httpServletRequest.getRequestURL());
        logger.info("This URL is no longer used, Redirect to /admin/login");

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+ "/admin/login");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("~~~~~~~~~~~~~~~~~~~~ FormerLoginInterceptor postHandle ~~~~~~~~~~~~~~~~~~~~");

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("~~~~~~~~~~~~~~~~~~~~ FormerLoginInterceptor afterCompletion ~~~~~~~~~~~~~~~~~~~~");
    }
}