package com.piattaforme.edilgiulianiback.securityconfig.old;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

public class FilterBean {
    //@Bean
    public FilterRegistrationBean<CompletedRegistrationFilter> loggingFilter(){
        FilterRegistrationBean<CompletedRegistrationFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CompletedRegistrationFilter());
        registrationBean.addUrlPatterns("/test/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
