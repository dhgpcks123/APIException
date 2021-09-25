package hello.apiexception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  예외 처리 Servlet 방식
 *  : WebServerSutomizer에서 요청한 path 처리를 이 곳에서 해주려고 한다.
 */

@Slf4j
@Controller
public class ErrorPageController {

    /**
     *  1. WAS `/error-page/500` 재 요청 -> 필터 -> 서불릿 -> 인터셉터 -> 컨트롤러(/error-page/500) -> view
     *  요청이 2번 일어난 것 : 하지만 웹 브라우저(클라이언트)는 서버 내부에서 이런 일이 일어나는지 전혀 모른다.
     *  또한 request 에 오류 정보를 담아서 넘겨준다.
     */
    // shift 2 -> RequestDispatcher 검색해서 들어가면 상수로 정의되어 있는 내용을 찾을 수 있음.
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request){
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));

        /**
         * 2. WAS `/error-page/500` 재 요청 -> 필터 -> 서불릿 -> 인터셉터 -> 컨트롤러(/error-page/500) -> view
         * -> 문제 : 필터, 인터셉터가 2번 호출 된다.. 로그인 인증 체크 한번 했는데 또 하게 된다. 비효율적!
         * 클라이언트 요청인지, WAS내부 요청인지 구분해야하는데... WAS에서 넘겨줄 때 DispatcherType을 추가 정보로 제공한다.
         */
        log.info("dispatcherType ={}", request.getDispatcherType());
    }
}
