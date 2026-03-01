# codex.md

## 작업 범위
- 모든 작업은 현재 프로젝트 디렉토리(`/Users/lucaskang/1.git-project/spring-essential/stomp-demo`) 내부에서만 수행한다.
- 본 문서는 `README.md`와 `build.gradle.kts`를 기준으로 작성한다.

## 프로젝트 정의
- 프로젝트명: `stomp-demo`
- 목적: STOMP + WebSocket 기반의 실시간 채팅 데모 애플리케이션
- 핵심 시나리오:
  - 여러 채팅방을 지원한다.
  - 여러 사용자가 채팅방에 입장해 메시지를 주고받는다.
  - 채팅방 입장 시 해당 채널을 구독한다.
  - 별도 인증 없이 사용자 이름과 채팅방 이름으로 입장한다.
  - 외부 MQ 브로커 없이 Spring Boot 내장 WebSocket 서버(in-memory)로 동작한다.

## 기술 스택
- 언어: Kotlin 1.9.25
- 런타임: Java 17
- 프레임워크: Spring Boot 3.5.11
- 주요 구성:
  - `spring-boot-starter-web`
  - `spring-boot-starter-websocket`
  - `spring-boot-starter-thymeleaf`
  - `jackson-module-kotlin`
  - `kotlin-reflect`
- 테스트:
  - `spring-boot-starter-test`
  - `kotlin-test-junit5`

## 작업 정의
- 현재 문서 기준으로 Codex 작업은 아래 순서로 진행한다.
1. 프로젝트 구조와 실행 흐름을 확인한다.
2. STOMP/WebSocket 채팅 기능의 현재 구현 상태를 점검한다.
3. 필요한 수정 사항, 누락 기능, 검증 항목을 정리한다.
4. 사용자 확인을 받은 범위까지만 코드 변경 또는 문서 보완을 수행한다.

## 산출물 기준
- 변경은 현재 프로젝트 디렉토리 내 파일만 대상으로 한다.
- 작업 중 판단의 기준은 우선 `README.md`, 다음으로 `build.gradle.kts`, 마지막으로 실제 코드 구현 상태로 둔다.
- 추가 구현 또는 수정이 필요할 경우, 먼저 작업 단위를 명확히 한 뒤 진행한다.
- 산출물의 경우 OOP 원칙을 준수하여 모듈화, 재사용성, 유지보수성을 고려한다.
- 디렉토리 구조는 /src/main/kotlin/com/example/stompdemo/ 아래에 패키지별로 구분하여 작성한다.
  - 패키지는 `config`, `controller`, `model`, `service`, `util` 등으로 나누어 작성한다.
- 