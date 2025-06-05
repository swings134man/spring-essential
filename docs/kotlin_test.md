# Kotlin Test 코드

- 자바에서는 Mocking 은 Mockito 를 사용하여 진행하지만,
- 코틀린에서는 MockK 를 사용한다.
> testImplementation("io.mockk:mockk:1.13.8")

#### Sample interface
- `Mockito`
  - `@Mock` : 으로 목 객체 생성
  - `@InjectMocks` : 으로 목 객체를 주입
  - `Given(mock.doSomething()).willReturn(result)` : 스탕일로 모킹수행
- `MockK`
  - `@MockK` : 으로 목 객체 생성
  - `@InjectMockKs` : 으로 목 객체를 주입
  - `every { mock.doSomething() } returns result` : 스탕일로 모킹수행
  - Mockito 와 유사함

```kotlin
class AnimalFarm(val pig: Pig, val cow: Cow)

class AnimalFarmTest {
    
    @MockK
    lateinit var pig: Pig

    @MockK
    lateinit var cow: Cow

    @InjectMockKs
    var animalFarm: AnimalFarm

    @Test
    fun testCalc() {
        // given
        every { pig.makeSound() } returns "oink"
        every { cow.makeSound() } returns "moo"
        
        // when 
        val result = animalFarm.feed()
        
        // then
        assertEquals("oink", animalFarm.pig.makeSound())
        assertEquals("moo", animalFarm.cow.makeSound())
    }
}
```

- 기능은 거의 동일하게 모두 제공된다
  - verify : 문법은 약간 다르지만 기능은 동일
  - argumentCaptor(Mockito) : slot(MockK) 를 활용하면 동일하게 구성가능
  - 코틀린 전용 기능 제공
    - 코루틴 관련된 모킹
    - 함수형 프로그래밍 관련된 모킹 기능 제공

---

### koTest (코틀린 테스트 코드)
- 코틀린 전용 테스트 프레임워크 
- Junit5 기반으로 작성(동작) 되어 있다.
- 기존에 비해 매우 간결하게 사용 가능함
- 라이브러리를 추가해줘야 한다.
  - `testImplementation("io.kotest:kotest-runner-junit5:5.7.2")`
- 테스트를 진행하는 코드 스타일을 `10가지` 제공한다.
  - `BehaviorSpec`: BDD 스타일 테스트
    - given, When, Then 을 명시적으로 코딩하여 사용하는 방식. (given 을 공유 할 수 있음)
    - 테스트 구조가 확실하게 만들어진다
    - 데이터를 공유하기 때문에, 중복 Mocking 을 최소화 할 수 있다. 
  - `StringSpec`: Util 이나, 간단한 테스트를 위한 테스트
    - 단순하게 테스트할 이름과, {} 안에 테스트 방식을 사용(1줄에 끝남)
- 다만
  - @ExtendWith 나 어노테이션 기반 Mock 기능이 제대로 지원하지 않는 경우가 많다.?
  - 그래서 직접 만들고 넣어줘야 한다.


> #### Assertions(KoTest)
> - assert ~... 와 같은것을 사용하는 방식에서 
>   - should, shouldNot, shouldBe, shouldNotBe 등과 같은 방식으로 변경된다.
>   - 'a' shouldBe 'a', isMarried.shouldBeTrue() 와 같은 방식
>   - 반복적으로 특정객체의 하위값들을 전부 비교하는 경우 assertSoftly 를 사용하여 여러 개의 assert 를 한번에 처리할 수 있다.
