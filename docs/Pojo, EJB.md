## Java POJO, EJB

### 1. POJO

- POJO: Plain Old Java Object 의 약자로, Java 객체를 일반적인 객체로 만들어 사용하는 것을 의미한다.
  - POJO 는 순수 Java 객체를 의미하며, 특정 프레임워크나 라이브러리에 종속되지 않는다.
   즉 Java 의 스펙에 정의된것 이외의 특정한 규칙을 따르지 않는다.
```Java
class Pojo {
    private String name;
    private int age;
    
    public String toString() {
        return "name: " + name + ", age: " + age;
    }
}
```


### 2. EJB

- EJB: Enterprise Java Beans 의 약자로, 
  - EJB 는 Java EE 의 스펙에 정의된 표준 기술로, Java EE 컨테이너에서 실행된다.
  - 다만 현재는 잘 사용하지 않는 기술이다.
  - EJB 는 세가지의 중요한 제한 존재
    1. no-argument Constructor: 인수 생성자가 없어야 한다.
    2. Getter/Setter: private 필드에 대한 Getter/Setter 가 존재해야 한다.
    3. Serializable: EJB 는 Serializable 인터페이스를 구현해야 한다. 
```Java
class Ejb implements Serializable {
    
    // no-argument Constructor 존재해선 안됨
//    public Ejb() {}
    
    private String name;
    private int age;
    
    // Getter/Setter ...
}
```