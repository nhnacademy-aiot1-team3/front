package com.front.config;

import com.front.interceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 애플리케이션의 MVC 구성을 위한 설정 클래스
 * @author 나채현
 * @version
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    /**
     * {@link RestTemplate}의 인스턴스 생성하고 스프링 빈으로 등록
     * @return 새로 생성된 {@link RestTemplate} 인스턴스
     * @since 1.0.0
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 애플리케이션의 인터셉터를 등록하는 메소드로,
     * {@link WebMvcConfigurer#addInterceptors(InterceptorRegistry)}를 오버라이드하여,
     * 커스텀 인터셉터를 스프링 MVC의 처리 흐름에 통합
     * @param registry 인터센터를 등록하기위한 {@link InterceptorRegistry} 인스턴스
     * @since 1.0.0
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor());
    }

}
