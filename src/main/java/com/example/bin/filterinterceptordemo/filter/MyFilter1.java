package com.example.bin.filterinterceptordemo.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilter1 implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter1.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.info("***************MyFilter1 init******************");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("------------MyFilter1-doFilter before---------");

        logger.info("-------" + request.getRemoteAddr() + ":" +request.getRemotePort() + "--------");

        String usrId = request.getParameter("usrId");
        if((usrId == null)||(usrId.isEmpty())||(Integer.parseInt(usrId)<=0)) {
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.getWriter().print("Parameter error!");
        } else if(Integer.parseInt(usrId) >=1000) {
            chain.doFilter(request, response);
        }

        logger.info("------------MyFilter1-doFilter after---------");
    }

    @Override
    public void destroy() {

        logger.info("***************MyFilter1 destroy******************");
    }
}