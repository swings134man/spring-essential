# Spring boot Kotlin 프로젝트로 변환에 관한 문서

- 기존의 Java 기반의 Spring boot 모듈 -> Kotlin 기반으로 변환하는 작업에 대해서 기술됨
- 변환 작업은 `prince-maker-kotlin` 모듈에서 진행됨
- `Versions`
  - Kotlin 을 사용하기 위해서는 최소 필요 버전들이 존재함
    - Spring boot: 2.5.2 이상 -> 3.x (java 17) 부터는 변경사항이 큼 
    - kotlin: 1.7.0 이상은 gradle 6.71 이상 필요 (7.x 이상 권장됨)
  - 다만 각 `Spring boot`, `kotlin`, `Gradle` 는 필요 버전마다 각 호환 되는 버전을 필수로 확인할것 !

<br>
---
## build.gradle (kts)

- 기존의 `build.gradle` 파일을 `build.gradle.kts`로 변경
- `build.gradle.kts` 파일은 Kotlin DSL을 사용하여 Gradle 빌드 스크립트를 작성하는 방식
  - 다만 꼭 `.kts` 로 변경하지 않아도 된다. 
  - IDE 에서 지원하는 기능들과, kotlin DSL 의 장점을 살리기 위한 목적이라면 변경하는것을 추천함.
<br><br>

>- 변환 작업하면서 느낀점은, `Spring boot`, `Kotlin` 의 버전에 따라 설정이 달라질수 있다는 점이다.
>  - allOpen 미사용이나, annotationProcessor, "-Xjsr305=strict" 설정 등이 그 예이다.

### Plugins
- `kotlin(”jvm”)`
  - JVM(Java Virtual Machine)에서 코틀린을 사용하기 위한 기본 플러그인
  - 이때 `Spring Boot` 의 버전과 JVM 버전이 호환되어야 한다.
    - 각 버전이 제대로 호환되지 않는다면(안정성), 오류가 발생 할 수 있음.
- `kotlin(”kapt”)`
  - Kotlin Annotation Processing Tool, 코틀린에서 어노테이션 처리(사용)
  - 롬복이나, queryDSL 과 같은 java 기반의 Annotation 을 kotlin 에서는 기본적으로 지원하지 않기에 `kapt` 를 사용하여 어노테이션을 처리할 수 있도록 해주는것.
- `kotlin(”plugin.jpa”)`
  - Entity class에 JPA 사용을 위한 매개변수 없는 기본 생성자를 자동 생성 (@NoArgsConstructor)
- `kotlin(”plugin.spring”)`
  - 스프링의 class proxy 기능을 정상 동작 시키기 위한 플러그인
  - 스프링 관련 어노테이션( @Controller, @Service, @Component, @Async, @Transactional 등) 이 붙은 class 를 상속이 가능한 open class 로 변경
    - 기본적으로는 `final` 로 선언되어 있기 때문에, Spring 의 class proxy 기능이 동작하지 않음
- `kotlin(”plugin.lombok”)`
  - 코틀린 → 자바(with lombok)을 정상 호출하게 도와주는 플러그인

---
### Library(라이브러리)
- 버전에 따른 변경사항이 존재한다.
  - kotlin 1.4.0 이전, java 8 까지는 `stdlib` 이라는 라이브러리를 추가해줬어야 함 -> 이후로는 내장되어 추가할 필요 없음
    - scope functions(apply, also, let, run, with), Collection 관련 추가 함수들(filter, map, etc) 등
- 현 문서를 작성하는 시점에서는 kotlin 을 위한것은 최소한으로 추가해도 가능하다?
```kotlin
implementation("org.jetbrains.kotlin:kotlin-reflect")
implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

// 다만 jackson module 의 경우 아래중 하나를 추가해야한다 (Bean 등록)
@Bean
fun objectMapper(): ObjectMapper = jacksonObjectMapper()
@Bean
fun objectMapper(): ObjectMapper = jsonMapper { addModule(kotlinModule()) }
```
- 각각 의 역할은 다음과 같다.
  - `kotlin-reflect`: 코틀린 리플렉션 기능을 사용하기 위한 라이브러리
    - Kotlin("plugin.jpa") 가 동작하는 과정에서 해당 라이브러리 필수.
  - `jackson-module-kotlin`: Jackson 라이브러리와 코틀린을 통합하여 JSON 직렬화/역직렬화를 지원하는 모듈


#### QueryDSL 
- QueryDSL 을 사용하기 위해서는 버전별로 설정의 차이가 조금 존재하긴하지만, kapt 설정추가 및, 버전명시만으로 사용할 수 있게 되었다.
- TODO: 다만 Boot 3.x, jvm 17 이상부터 `jakarta` 관련 변경사항이 존재하고, 블로그들에서 관련된 implementation 를 더 추가하라고 함 -> 이건 테스트 해보고 판단예정
```kotlin
dependencies {
  val querydslVersion = "5.0.0"
  implementation("com.querydsl:querydsl-jpa:$querydslVersion")
  kapt("com.querydsl:querydsl-jpa:$querydslVersion:jpa")
}
```

---
### 추가적인 설정(Tasks, Compile)

- Kotlin DSL을 사용하여 Gradle 빌드 스크립트를 작성할 때, 추가적인 설정이 필요할 수 있다.
  - `Kotlin Compiler`, `kapt 활성화`

#### 필수 설정 list (추가적인것은 없음)
```kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile // 최상단 위치

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

// kotlin
kapt {
	keepJavacAnnotationProcessors = true
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict") // Nullability Annotation Checking
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// 이건 필요 없음 설명을 위한것.
allopen{
    annotation("...entity")
    annotation("...MappedSuperclass")
    annotation("...Embedded")
}
```
- `compileOnly` 설정은 `annotationProcessor` 사용을 위한 설정
- `kapt`
  - 기본적으로 kapt 는 javac 에서 어노테이션 프로세서들을 비활성화 한다.
  - lombok 을 사용하기 위해서는 `true` 설정을 통해 활설화가 필요하다.
- `kotlinCompile` Options
  - `jsr305` 설정을 하기 위함. 
  - `-Xjsr305=strict` 설정은 JSR-305 어노테이션을 엄격하게 처리하도록 지정
  - Spring 의 Nullability Annotation 지원을 받을 수 있음 -> @Nullable, @NotNull 등
    - kotlin 에서 java 코드 호출시 null 체크 강화
    - null 다루는 버그를 컴파일 시점에 잡아낼 수 있음
    - 즉 java 라이브러리 및 spring 을 연동할 때 null 관련 문제를 줄일 수 있다.
- `allopen`
  - 1.4.0 이전 버전에서는 JPA 를 사용할때 `@Entity`, `@MappedSuperclass`, `@Embedded` 등의 어노테이션이 붙은 클래스가 기본적으로 `final`로 선언되어 있어 상속이 불가능했다.
  - 따라서 해당 설정을 통해 이러한 어노테이션이 붙은 클래스들을 자동으로 `open` 클래스로 변경하여 상속이 가능하도록 한다.
  - 이 설정은 Kotlin 1.4.0 이후 버전에서는 필요하지 않다 -> 기본 내장 (kotlin.plugin.jpa)



