# Coroutine(코루틴) 적용

>Spring MVC 에서는 Coroutine 을 사용할 수 있지만, 한계점이 명확하고 코루틴의 장점을 극대화 할 수 없다!
>> #### 코루틴의 효율이 낮은 이유들
>>> - Request(요청) 1개 -> Tomcat(톰캣) Thread 1개 -> 1개의 요청은 1개의 Thread 에서 처리되며 응답시까지 Block 된다.
>>> - Tomcat 은 suspend 상태를 지원하지 않는다(모른다) -> 일반 함수로 처리 함
>>> - Thread Pool 의 한계 201 번쨰 요청부터는 Pending 상태로 대기한다.
>>> - DB Call 경우도 `DB Driver` + `Connection Pool` 이 비동기를 지원해야지만 의미가 있다. (다만. Dispatcher.IO 를 사용하여 멀티쓰레드로 돌릴수 있음)
- 다만 요청 내부의 병렬처리에는 유용하게 사용될 수 있다. -> 병렬 처리
- 또한, `WebClient` 를 이용하여 `외부 API 호출`을 비동기로 처리하는 경우 효율적으로 사용 가능하다.
> Spring + Coroutine 의 효율을 극대화 하기 위해서는 `Spring WebFlux` 를 사용해야 한다.
- 이러한 단점들 때문에, MVC 에서는 `Completable Future` 를 사용하는것이 더 효율적일것 같다 


---

### Coroutine 설정 
- `build.gradle.kts` 에서 의존성 추가
```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.0")
```
- 해당 2개의 의존성이 필수이다. 코루틴을 사용하기 위해서는 Reactor 의존성이 필요한데, 컨트롤러에서 suspend 를 사용하기 위해서는 reactor 가 필요하기 때문
- 

--- 
### 코드 설명 및 테스트 결과 

- findById() : 2개의 서비스를 동시에 호출하여 결과를 합친 후 Return 하는 예제
  - 오히려 코루틴을 적용하지 않은 경우가 성능이 더 좋게 나왔다. -> 각각의 서비스 호출했을떄 내부가 블로킹이라 Context Switch(오버헤드) 발생하여 더 느린것으로 보임
    - TODO: Dispatcher.IO 를 사용한 경우도 비슷하여 추가 테스트 필요할듯


TODO: WebClient 를 사용한 비동기 API 호출 예제 추가








