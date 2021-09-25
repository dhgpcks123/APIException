# springboot Exception

- 해당 경우 기본 오류 매커니즘을 사용하도록 BasicErrorController 사용한다.
- 기본 설정이 다 만들어져있음..  
  ( 클래스 만든 거 하나도 없음. ErrorPageController도, WebServerCustomizer도 안 만듬
  그냥 요청 받았을 때 new Exception 주면 됨)

> 오류 페이지만 해당 경로에 저장하면 됨!

template > error

"뷰 선택 우선 순위"

1. 뷰 템플릿
   resources/templates/error/500.html
   resources/templates/error/5xx.html
2. 정적 리소스
   resources/static/error/400.html
   resources/static/error/404.html
   resources/static/error/4xx.html
3. 적용 대상이 없을 때 뷰 이름
   resources/static/error/error.html