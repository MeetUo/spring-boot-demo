package com.rsna.demo.config;


import com.rsna.demo.innterceptor.LoginInterceptor;
import com.rsna.demo.innterceptor.ProcessInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        // RequestInterceptor()为自己定义的拦截器
        List<String> expath = new ArrayList<>();
        expath.add("/login.html");
        expath.add("/index.html");
        expath.add("/**/**/**.js");
        expath.add("/user/**");
        registry.addInterceptor(new ProcessInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(expath);
    }
}
