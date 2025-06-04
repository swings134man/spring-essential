# Kotlin 변환시 Class(용도)별 변환 방법

- util, dto, entity, repository, service, controller 등 각 용도별로 Kotlin 으로 변환하는 방법에 대해서 기술함

---
## 1. Util Class
- 유틸 클래스는, 의존성이 낮고 의도가 비교적 명확하여 테스트가 쉬움
- 클래스를 인스턴스로 생성할 필요가 없다 -> static Method 사용 혹은 `@UtilityClass` 어노테이션 사용(Lombok) static method 로 변환
  - `@UtilityClass` 어노테이션은, 클래스 내부의 모든 메소드를 static 으로 변환해줌, 생성자를 private 으로 막아둔다(인스턴스 생성 못하게), 또한 클래스는 final 로 변경됨
> - object 키워드 활용, 싱글톤으로 생성
> - `package level function` 혹은, `top level function` 으로 함수만 생성

## 2. DTO Class
- DTO 의 의존성의 정도가 다름 -> 중첩 DTO 같은 경우
- 간단한 구조의 DTO 부터 변환 수행 -> 간편함
- nullable 체크 주의
  - DB, 외부 API 요청/응답 등 외부 환경과 연관된 DTO 는 주의 필요함.
- Lombok 으로 생성되던, getter, setter, toString, equals, 생성자 등은 상황에 맞춰 수정 필요함
  - getter/setter 는 코틀린에서 기본적으로 프로퍼티가 제공됨
  - 생성자: intellij 코틀린 변환기 변환 후 코틀린 `주생성자 스타일`로 바꾸는것을 추천 -> 기본적으로 필드형태로 만들어줌!
  - toString/equals : data class 를 사용하는것이 좋음
> DTO 는 `data class` 로 `주 생성자 스타일`로 만드는것이 편함, Null 주의할것.
> - intellij 변환기 사용후 Annotation 위치 수정필요
> - `?` = null 로 초기화를 추천함(Nullable 하게) -> 처음 변환시 null 관련 오류 발생 많이함.
> - Field 형태를 `주생성자` 형태로 변경

## 3. Entity Class
- Entity 에 `MappedSuperClass` 가 존재하는 경우 -> 공통 엔티티 클래스를(공통 필드) 만들어 상속받게 하는경우를 뜻함
  - BaseEntity 부터 수정할것
  - 코틀린 전용 별도의 (Kt)BaseEntity 작성하여 상속받게 하는것을 추천
    - 코틀린은 코틀린것을 상속받도록, 자바는 자바것으로
- @Embedded, @Embeddable 과 같은 vo 를 사용하는 경우
  - 하위 클래스도 코틀린으로 변환하는게 편리하다
- 주생성자에 `@Size, @Min` 과 같은 Validation 어노테이션은 그대로 사용하면 적용되지 않으므로
  - Annotation use-site-target 을 사용하자.
  - 예시: `@field:size(min = 1, max = 10), @field:Min(1)`
> - 하위 클래스, 즉 공통 필드가 존재하는 `Entity` 부터 변환
> - java, kotlin 이 혼용될 경우, kotlin 전용 `BaseEntity` 를 만들어 코틀린,자바 각각 상속받게 할것
> - `Lombok` 은 최대한 제거하자
> - 주생성자에는 `@field:` 어노테이션을 사용하여, Validation 어노테이션을 적용하자

## 4. Service Class
- 로직 복잡도가 높고, 비즈니스 중요도 높음
- 클래스가 복잡한 경우, 변환 실패 혹은 의도와 약간 다르게 변환되는 경우가 존재함.
  - 클래스가 너무 큰 경우, 역할을 적절히 나눠서 분리한 후 진행하는것이 유리함
  - 테스트를 강화한 후에 진행
- 복잡도가 높은 로직은 코틀린의 여러 기능 활용하여 정리 가능
> - `Service` 는 적절한 크기로 나눠서 진행 하는것이 유리할 수 있음 -> 역할을 쪼개자
> - 테스트를 잘 만들어 두자

## 5. Controller Class
- 주의할점
  - `objectMapper` 를 재정의한 경우?
    - jackson-module-kotlin 을 추가한 후 `kotlinModule` 을 등록해야 함
    - 코틀린 DTO 의 직렬화/역직렬화를 위한 기본 생성자 자동 생성해준다.
  - `@Valid` 어노테이션 위치 오류 수정
- Request Body 의 field 는 Nullable 로 설정하는것이 편리함.
  - `@NotNull` 밸리데이션 처리로 적절히 처리 가능
  - 해당 필드가 `not null` 인 경우 `MissingKotlinParameterException` 발생하고 처리가 까다로움
  - Long, Int 와 같은 원시타입의 경우 필드가 없는경우 '0' 기본값이 할당되는 문제 발생




