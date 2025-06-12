# Kopring-jpa-demo 
- Kotlin + Spring Boot + JPA + QueryDSL Sample Project

> ### Versions
> - java 17
> - Kotlin 1.9.25
> - spring boot 3.3.12
> - PostgreSQL 17-alpine
> - OpenFeign QueryDSL 7.0

- DTO 관련 변환 응답 -> `BoardEntity` 참고
- QueryDSL 관련 -> `dslEntity` 참고

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
---
## QueryDSL 사용 Tips
> `QueryDsl` 은 사실상 개발중단 되었으며, `OpenFeign` + `KSP` 로 대체되고 있다. -> `kapt`-> `KSP` 사용 권장
> - OpenFeign QueryDSL 로 마이그레이션 가능, 6.10.1 버전은 Order 관련 취약점을 수정한 버전이며, Kapt 를 지원함.(KSP 마이그레이션 안해도된다.)
> - 가장 안정화된 버전은 `6.11`, 가장 최신 버전은 `7.0` 이다.

- QueryDSl 을 사용하기 위해서는 기존의 Java 기반에서 사용하던것과 유사하다.(설정, 사용법)
- 다만 Kotlin 에서는 `kapt` 를 사용하여 Annotation Processor 를 사용해야 한다.
  - 이건 QClass 생성하기 위해서 필요한 작업이다 -> 코틀린 컴파일러는 Annotation Processor 를 지원하지 않기 때문
- 다만 생성될때 Kotlin 이 아닌 Java 소스 파일로 생성된다.

> 아래의 설정은 `최신`, `옛날` 2가지 버전이 있는데 gradle 8.x^, Kotlin 1.9.x, querydsl 5.1^ 부터는 `최신`을 사용하면 된다.  
- 최신버전의 경우  `build/generated/source/kapt/main` 경로에 `QClass` 파일이 기본적으로 생성된다. 
  - `sourceSets` 를 통해 `/build/...` 아래경로가 컴파일/소스 경로로 인식되지 않는걸 인식하게 해주는것.(QClass 를 인식하기 위함)  


### build.gradle.kts 설정 (OpenFeign QueryDSL 버전) -> 이걸 사용할것!! (6.11 이상)
```kotlin
dependencies {
  // Querydsl
  implementation("io.github.openfeign.querydsl:querydsl-jpa:7.0")
  kapt("io.github.openfeign.querydsl:querydsl-apt:7.0:jakarta")
}

// Query DSL Configuration
sourceSets["main"].java.srcDirs("build/generated/source/kapt/main")

tasks.named("clean") {
  doLast {
    file("build/generated").deleteRecursively()
  }
}
```

### build.gradle.kts 설정 (최신 버전)
```kotlin
dependencies {
  // Querydsl
  implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
  kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
}

// Query DSL Configuration
sourceSets["main"].java.srcDirs("build/generated/source/kapt/main")

tasks.named("clean") {
  doLast {
    file("build/generated").deleteRecursively()
  }
}
```


### build.gradle.kts 설정 (옛날 버전)
```kotlin
dependencies {
  // Querydsl
  implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
  kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
  kapt("jakarta.annotation:jakarta.annotation-api")
  kapt("jakarta.persistence:jakarta.persistence-api")
}

// Query DSL Configuration
val generated = file("src/main/generated")

// querydsl QClass 파일 생성 위치를 지정
tasks.withType<JavaCompile> {
  options.generatedSourceOutputDirectory.set(generated)
}

// kotlin source set 에 querydsl QClass 위치 추가
sourceSets {
  main {
    kotlin.srcDirs += generated
  }
}

// gradle clean 시에 QClass 디렉토리 삭제
tasks.named("clean") {
  doLast {
    generated.deleteRecursively()
  }
}

// kapt 설정 -> 이건 필요 없음
kapt {
    generateStubs = true // QueryDsl(gradle 8.x, kotlin 1.9.x 부터 필요없음)
}
```
