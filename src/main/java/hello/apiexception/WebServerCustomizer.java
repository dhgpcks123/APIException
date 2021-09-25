package hello.apiexception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 *  예외 처리 Servlet 방식
 *  : 관련 오류가 발생하면 path 요청할 수 있도록 spring boot가 커스터마이징을 제공해준다.
 */
@Component // 스프링 빈에 등록
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory){

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404ç");
        // HttpStatus.NOT_FOUND : 404 -> 404가 발생하면 /error-page/400으로 이동해라.
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        // HttpStatus.NOT_FOUND : 500 -> 500 발생하면 /error-page/500으로 이동해라.
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
        // Runtime 상속들 전부 해당 Exception 타게 됨. 물론 좁은 예외 있으면 우선순위를 가짐.

        // 위의 에러 3가지를 등록해줍니다.
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
