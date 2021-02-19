
* 过滤器(Filter)

    依赖于Servlet容器（所以不能使用spring容器的资源）
    实现是基于函数回调
    可以对所有请求进行过滤


* 拦截器(Intercepter)

    依赖于web容器（可以使用spring容器的资源）
    实现是基于反射
    只能对controller请求进行拦截，对直接访问静态资源的请求无法拦截

#### 执行顺序

Filter前处理->Intercepteor前处理->action->Interceptor后处理->Filter后处理


#### Filter的作用
*   在HttpServletRequest到达Servlet之前，检查，修改甚至拦截HttpServletRequest。
*   在HttpServletResponse到达客户端之前，修改或拦截HttpServletResponse。
*   检查用户请求，根据请求过滤用户非法请求。
*   详细记录某些特殊的用户请求。
*   对非标准编码的请求解码

#### Inteceptor的作用
1.  日志记录：记录请求信息的日志，以便进行信息监控、信息统计、计算 PV（Page View）等；
2.  权限检查：如登录检测，进入处理器检测是否登录；
3.  性能监控：通过拦截器在进入处理器之前记录开始时间，在处理完后记录结束时间，从而得到该请求的处理时间。（反向代理，如 Apache 也可以自动记录）
4.  通用行为：读取 Cookie 得到用户信息并将用户对象放入请求，从而方便后续流程使用，还有如提取 Locale、Theme 信息等，只要是多个处理器都需要的即可使用拦截器实现

#### javax.servlet.Filter接口：

*   void init(FilterConfig config):Filter的初始化。
*   void destory():Filter销毁前，资源的回收。
*   void doFilter(ServletRequest request,ServletResponse response,FilterChain chain):在调用chain.doFilter()方法前可以对request进行预处理，在调用chain.doFilter()方法后可以对response进行后处理


#### HandlerInterceptor接口：
*   preHandle在请求处理之前进行调用。可以在这个方法中进行初始化或预处理或判断来决定请求是否要继续进行下去。
    *   返回值为true时表示继续执行，会继续调用下一个Interceptor 的preHandle 方法，如果已经是最后一个Interceptor 的时候就会是调用当前请求的Controller方法
    *   返回为false 时，表示请求结束，后续的Interceptor 和Controller 都不会再执行
    
*   postHandle preHandle 方法的返回值为true 时才能被调用postHandle 方法，Controller 方法调用之后执行，但是它会在DispatcherServlet 进行视图返回渲染之前被调用，所以我们可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作
    
*   afterCompletion在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。这个方法的主要作用是用于进行资源清理工作的。


#### Filter实现
```
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
```

Interceptor实现
```
public class LogInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    
    @Autowired
    UserPrivilegeService privilegeService;
    
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("*********** LogInterceptor preHandle *******************");
        long startTime = System.currentTimeMillis();
        httpServletRequest.setAttribute("startTime", startTime);
        return true; 
    }
    
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("*********** LogInterceptor postHandle *******************");
    }
    
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("*********** LogInterceptor afterCompletion *******************");
        long startTime = (Long) httpServletRequest.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        logger.info("End Time: " + endTime);
        logger.info("Time Taken: " + (endTime - startTime));
    }
}
```

#### 添加Filter
```
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new MyFilter1());
        bean.addUrlPatterns("/login");
        bean.setOrder(1); //值越小优先级越高
        return bean;
    }
 }
```

#### 添加Interceptor
```
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor());
    }
}
```
