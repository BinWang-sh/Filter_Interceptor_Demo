package com.example.bin.filterinterceptordemo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilter2 implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter1.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        logger.info("***************MyFilter2 init******************");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("------------MyFilter2-doFilter before---------");

        String channelId = request.getParameter("channelId");
        if((channelId == null)||(channelId.isEmpty())) {
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.getWriter().print("Parameter error!");
        } else {
            chain.doFilter(request, response);
        }

        logger.info("------------MyFilter2-doFilter after---------");
    }

    @Override
    public void destroy() {

        logger.info("***************MyFilter2 destroy******************");
    }
}
