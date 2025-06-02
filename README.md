## Spring Essential Repo

### 1. info
- This Repository is about study Spring Framework
    - Spring Boot
    - Spring Cloud


### 2. Modules 
1. `boot-basic`: Spring boot 의 기본 원리및 사용법
   - v3.2.9
   - mariaDB, JPA
<br/><br/>
2. `sample-lib`: Spring boot 의 Library 생성, 설정 모듈 
   - v3.3.6
   - Spring web
     <br/><br/>
3. `lib-test-module`: sample-lib 모듈을 사용하는 테스트 모듈
   - v3.3.6
   - Spring web
     <br/><br/>
---
#### 2-2. Spring Clouds
4. `cloud-gateway`: Spring Cloud 의 Gateway 모듈
   - v3.3.9
   - Spring cloud gateway
     <br/><br/>
---
#### 2-3. Kotlin Spring
5. 'prince-maker-kotlin': java -> kotlin 변환 테스트 프로젝트
   - java, gradle 등과 같은 버전들은 추후 계속 변경될 예정
   - `init 시점 versions`
     - java 11
     - spring 2.1.x
     - gradle 5.6.4
   - `변경`
     - java 17
     - spring 3.2.x
     - gradle 8.13.1


### 3. Settings
> ## Clone 이후 Intellij 프로젝트 Settings
>> 1. gradle syncs 진행 
>> 2. project structure -> modules -> 각 모듈별 jdk 설정 
