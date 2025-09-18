package vn.ngocthang.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.ngocthang.filter.AdminFilter;
import vn.ngocthang.filter.UserFilter;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setName("AdminFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<UserFilter> userFilter() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setName("UserFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter() {
        FilterRegistrationBean<ConfigurableSiteMeshFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ConfigurableSiteMeshFilter());
        registration.addUrlPatterns("/*");
        registration.setName("SiteMeshFilter");
        registration.setOrder(0);
        return registration;
    }
}
