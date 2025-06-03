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
> - 하위 클래스, 즉 공통 필드가 존재하는 `Entity` 부터 변환
> - java, kotlin 이 혼용될 경우, kotlin 전용 `BaseEntity` 를 만들어 코틀린,자바 각각 상속받게 할것
> - `Lombok` 은 최대한 제거하자

