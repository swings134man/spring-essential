## API 명세

### 1. Page Endpoint

#### `GET /`
- 메인 페이지를 조회한다.
- 기능:
    - 채팅방 목록 조회
    - 채팅방 생성
    - 사용자 이름 입력 후 채팅방 입장

#### `GET /rooms/{roomId}?username={username}`
- 특정 채팅방 페이지에 입장한다.
- Query Parameter:
    - `username`: 채팅방 입장 사용자 이름
- 동작:
    - 채팅방이 존재하고 `username` 값이 비어있지 않으면 채팅방 화면 렌더링
    - 조건이 맞지 않으면 메인 페이지로 리다이렉트

### 2. REST API

#### `GET /api/rooms`
- 채팅방 목록을 조회한다.

응답 예시:
```json
[
  {
    "id": 1,
    "name": "Lobby",
    "creatorName": "lucas",
    "createdAt": "2026-03-01T17:00:00"
  }
]
```

#### `POST /api/rooms`
- 채팅방을 생성한다.
- Request Body:
```json
{
  "name": "Lobby",
  "creatorName": "lucas"
}
```

- 응답:
    - `201 Created`

응답 예시:
```json
{
  "id": 1,
  "name": "Lobby",
  "creatorName": "lucas",
  "createdAt": "2026-03-01T17:00:00"
}
```

- 유효성 규칙:
    - `name`은 공백일 수 없음
    - `creatorName`은 공백일 수 없음

#### `DELETE /api/rooms/{roomId}`
- 채팅방을 삭제한다.
- 삭제는 채팅방 개설자만 가능하다.
- Request Body:
```json
{
  "requesterName": "lucas"
}
```

- 응답:
    - `204 No Content`: 삭제 성공
    - `400 Bad Request`: 요청자 이름이 비어 있음
    - `403 Forbidden`: 요청자가 개설자가 아님
    - `404 Not Found`: 채팅방이 존재하지 않음

- 참고:
    - 삭제 성공 직전, 해당 채팅방을 구독 중인 사용자들에게 STOMP로 삭제 이벤트를 전송한다.
    - 채팅방 내부 사용자는 삭제 이벤트 수신 후 `alert` 표시, WebSocket 연결 해제, 메인 페이지 이동 처리된다.

### 3. STOMP / WebSocket 명세

이 프로젝트는 Spring WebSocket + STOMP + Simple Broker 조합을 사용한다.

#### WebSocket Endpoint

#### `GET /ws`
- SockJS 기반 WebSocket 연결 엔드포인트
- 클라이언트는 이 endpoint로 연결한 뒤 STOMP 프로토콜을 사용한다.

#### STOMP Prefix 설정
- Application Destination Prefix: `/app`
- Broker Prefix: `/topic`

의미:
- `/app/*`: 클라이언트에서 서버로 보내는 메시지 목적지
- `/topic/*`: 서버가 구독자들에게 브로드캐스트하는 목적지

#### 메시지 발행

#### `SEND /app/chat.send/{roomId}`
- 클라이언트가 채팅 메시지를 서버로 전송할 때 사용한다.

Request Body 예시:
```json
{
  "sender": "lucas",
  "content": "hello",
  "type": "TALK"
}
```

지원하는 `type` 값:
- `ENTER`: 채팅방 입장 알림
- `TALK`: 일반 채팅 메시지
- `LEAVE`: 채팅방 퇴장 알림
- `ROOM_DELETED`: 서버가 채팅방 삭제 시 내부적으로 브로드캐스트하는 이벤트

동작:
- 서버는 `roomId`에 해당하는 채팅방이 존재할 경우 메시지를 생성한다.
- 생성된 메시지는 `/topic/rooms/{roomId}`로 브로드캐스트된다.

#### 메시지 구독

#### `SUBSCRIBE /topic/rooms/{roomId}`
- 특정 채팅방의 메시지를 구독한다.
- 해당 채널로 아래 형식의 메시지가 전달된다.

응답 예시:
```json
{
  "roomId": 1,
  "sender": "lucas",
  "content": "hello",
  "type": "TALK",
  "timestamp": "2026-03-01T17:01:00"
}
```

삭제 이벤트 수신 예시:
```json
{
  "roomId": 1,
  "sender": "lucas",
  "content": "채팅방이 삭제되었습니다. 메인 페이지로 이동합니다.",
  "type": "ROOM_DELETED",
  "timestamp": "2026-03-01T17:02:00"
}
```
