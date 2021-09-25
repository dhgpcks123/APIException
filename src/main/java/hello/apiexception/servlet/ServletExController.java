package hello.apiexception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 예외 처리 Servlet 방식
@Slf4j
@Controller
public class ServletExController {

    /**
     * 오류 방식 1 : new Exception
     * 예외를 터트림
     * WAS <return- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외 발생)
     */
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
        // 서버 내부에서 처리할 수 없는 예외가 생겼다. 500 : Internal Server Error
    }

    /**
     * 오류 방식 2: sendError
     * 예외를 터트리지 않음.
     * WAS <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(response.sendError())
     */
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        //오류 방식 1과 다르게 response 상태코드를 넣을 수 있음!
        response.sendError(404, "404 오류!");
    }
    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException{
        response.sendError(500, "500 오류!");
    }
}
