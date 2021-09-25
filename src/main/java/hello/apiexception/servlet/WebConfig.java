package hello.apiexception.servlet;

import hello.apiexception.servlet.filter.LogFilter;
import hello.apiexception.servlet.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 1. LogFilter 설정
     * filter xml 설정을 java Config 파일로!
     * REQUEST, ERROR 요청 모두 필터가 호출된다.
     * 아무것도 넣지 않으면 REQUEST 가 디폴트 값이다. 즉 기본값 사용하면 ERROR 페이지는 필터를 거치지 않게 된다.
     *
     * 물론 오류 페이지 요청 전용 필터를 적용하고 싶으면 DispatcherType.ERROR 만 지정해주면 된다.
     */
//    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*"); // 모든 요청
//        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ERROR);
        return filterRegistrationBean;
    }

    /**
     * 2. Interceptor 설정
     * dispatcher Type 없어요... 대신 오류 페이지 경로를 빼요
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "*.ico", "/error", "/error-page/**"); // 오류 페이지 경로
    }
}
