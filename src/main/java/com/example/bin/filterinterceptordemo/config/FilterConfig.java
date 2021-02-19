package com.example.bin.filterinterceptordemo.config;

import com.example.bin.filterinterceptordemo.filter.MyFilter1;
import com.example.bin.filterinterceptordemo.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new MyFilter1());
        bean.addUrlPatterns("/login");
        bean.setOrder(1);
        return bean;
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean2(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new MyFilter2());
        bean.addUrlPatterns("/login");
        bean.setOrder(2);
        return bean;
    }

}
