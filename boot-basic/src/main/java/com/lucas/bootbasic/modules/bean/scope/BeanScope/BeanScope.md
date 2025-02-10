## Spring Bean Scope

- Spring Bean Scope 란?
Spring 컨테이너가 관리하는 'Bean' 이 생성되고, 유지되는 방식을 지정하는것.

- xml 로 설정할 수 있고, java config 로 설정할 수 있다.
- Spring Bean Scope 종류
    - Singleton : Spring 컨테이너에 하나의 Bean 객체만 생성되어 모든 요청에 대해 동일한 객체를 반환
    - Prototype : 요청할 때마다 새로운 Bean 객체를 생성하여 반환
      - 해당 scope 는, 생성, DI주입, 초기화 만 처리해주기 때문에, 소멸(Destroy) 처리는 개발자가 직접 처리해야한다.
    - Request : HTTP 요청마다 새로운 Bean 객체를 생성하여 반환: web-aware 컨택스트에서만 사용가능 - ex. Applicaiton context만 유효
    - Session : HTTP 세션마다 새로운 Bean 객체를 생성하여 반환: web-aware 컨택스트에서만 사용가능 - ex. Applicaiton context만 유효

기본적으로 @Scope 를 지정하지 않으면 Singleton Scope 로 지정된다.

```java
// Component
@Component
@Scope("prototype")
class ProtoTypeBean {
}

// Configuration
@Configuration
class configClass {
    
    @Bean
    @Scope("prototype")
    public ProtoTypeBean protoTypeBean() {
        return new ProtoTypeBean();
    }
}
```

--- 
### 문제점
- Singleton Scope 는 Bean 이 공유되기 때문에, 멀티스레드 환경에서 문제가 발생할 수 있다.
- Prototype Scope 는 Bean 이 생성될 때마다 새로운 객체를 생성하기 때문에, 메모리 누수가 발생할 수 있다.
- Singleton Bean 에서 Prototype Bean 을 참조(즉 주입받을)때 문제가 발생할 수 있다.
  - 싱글톤 빈은 생성시점에 주입받은 프로토타입 빈을 계속 참조하기 때문에, 프로토타입 빈이 변경되지(새로운 빈을 주입X) 않는다. 라는 문제가 있음
  - 해결방법
    - 이런 경우를 대비해서 prototype bean 클래스에서 @Scope 안에 옵션을 사용하도록 한다(ProxyMode.TARGET_CLASS)
    - @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
      - proxyMode 의 default 는 ScopedProxyMode.DEFAULT 이며 이 경우 문제가 발생한다.
      - ScopedProxyMode.TARGET_CLASS 는 프록시를 사용하여, 싱글톤 빈이 프로토타입 빈을 참조할 때마다 새로운 프록시를 생성하여 참조하도록 한다.
      - ScopedProxyMode.INTERFACES 는 프록시를 사용하여, 프로토타입 빈이 인터페이스를 구현한 경우 사용한다.
    - proxyMode 는 대상 Bean 을 Proxy 로 감싸서 참조 하도록 하는것이다.

```java
// 이 경우 문제 발생함
@Component
@Scope("prototype")
class ProtoTypeBean {
    // ...
}
// ---------    
@Component
@Scope
class SingletonBean {
    @Autowired
    private ProtoTypeBean protoTypeBean;
    // ...
}
```