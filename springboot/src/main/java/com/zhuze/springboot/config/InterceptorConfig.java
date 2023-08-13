package com.zhuze.springboot.config;

import com.zhuze.springboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: InterceptorConfig
 * Package: com.zhuze.springboot.config
 * Description:
 *
 * @Author 朱泽
 * @Create 2023/5/3 13:35
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")     //拦截所有请求，通过判断token是否合法来决定是否需要登陆
                .excludePathPatterns("/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
