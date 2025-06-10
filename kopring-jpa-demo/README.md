# Kopring-jpa-demo 
- Kotlin + Spring Boot + JPA + QueryDSL Sample Project

> ### Versions
> - java 17
> - Kotlin 1.9.25
> - spring boot 3.3.12
> - PostgreSQL 17-alpine


> ### Tips
> - Entity 자체를 return 할때 발생하는 문제 
>>   - 연관관계에 있는 Entity 가 `fetch lazy` 여도 조회 된다. -> N + 1
>>   - 해결 방법
>>     - Entity 를 DTO 로 변환해서 return 한다. -> 제일 많이 사용되는 방법
>>     - `@Query` 에서 `fetch join` 을 사용한다.
>>     - `fetch lazy` + `@JsonIgnore` 를 사용한다. -> Entity 를 DTO 로 변환하지 않고도 해결 가능
>
> 
> - 순환참조 문제
>> - `@JsonManagedReference` 와 `@JsonBackReference` 를 사용한다.
>> - @JsonIgnore 를 사용한다.? 
>> - 이 문제는 서로의 참조 계속해서 순환할때 발생하는 문제
>>   - 참조를 끊어줌으로써 해결 가능한 방법이다.