# Spring Cloud Gateway Module

## 1. Info
- Spring Cloud Gateway 모듈
- 특정 Request 들에 의해 필터링 및 라우팅을 수행하는 Gateway 모듈
- Global Filter 를 통한 Logging 기능
- actuator 를 통한 Monitoring 기능




## 2. Spring Cloud Gateway(SCG) 핵심 개념 
- Spring Cloud Gateway(SCG) 는 netty 기반인 Webflux 사용, MVC 또한 존재하지만 Webflux 를 사용하는 것을 권장
- 부하분산(Load Balancing), Logging, Monitoring, 인증 및 권한관리(Spring Security), 
- SCG 는 여러가지 Filter 를 사용하여 routing 을 수행함.
  - Path Predicate: Request 의 Path 를 기반으로 라우팅
  - uri Predicate: Request 의 uri 를 기반으로 라우팅 (https://example.org)
- 기본적으로 `yml` 파일을 수정하여 설정을 변경할 수 있지만
  - Java Code 로 설정을 변경할 수도 있다 -> `RouteLocator` 를 구현하여 사용
> - Route
>   - Client 의 Request 를 어디로 보낼지 결정함.
>   - /api/orders -> order-service 로 전송
> - Predicate(조건)
>   - 특정 조건(ex: 경로, HTTP 헤더, Req IP) 등에 따라 Request 를 라우팅, 필터링, 요청 거부 등등
>   - 특정 HTTP Method 만을 허용 여부 결정
>   - User-Agent 및 Header 에 따라 허용 여부 결정
>   - Query String 에 특정값에 따라 허용 여부 결정
>   - 특정 HOST 로 들어오는 요청 허용 여부 결정
>   - 특정 시간 이전,이후,기간 요청 허용 여부 결정
>   - 여러 조건들을 AND 연산으로 조합 가능함.
> - Filter
>   - Request/Response 를 수정(인증, 로깅, CORS, 헤더 조작)
- Spring Cloud Eureka 를 같이 사용한다면 `lb://` 를 통해 Load Balancer 를 사용할 수 있다.
  - Eureka 의 등록된 Service name 을 통해 사용 하는 방식.
  - 별도의 Load Balancer 를 사용하지 않아도 된다.
  
> Logging 을 Gateway 모듈에서 직접 수행하면, 부하가 발생할 수 있음(Blocking)
> - MQ 를 사용하여 별도 Logging Module 에서 처리하는게 좋은 방법.
> - -> 고성능, 확장성, 비동기, 장애발생시 로깅 시스템만 영향 등 이점이 훨씬 많음
