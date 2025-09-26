package com.lucas.bootbasic.modules.events;

import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

/**
 * @package : com.lucas.bootbasic.modules.events
 * @name : EventTest.java
 * @date : 2025. 9. 26. 오후 4:46
 * @author : lucaskang(swings134man)
 * @Description: EventListener Test
**/
@SpringBootTest
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EventTest {

    @Autowired
    MsgEventPubService msgEventPubService;

    @Test
    void 정상_이벤트_발행_테스트() throws Exception {
        // given
        Long id = 1L;
        String message = "hello";

        // when
        msgEventPubService.saveAndMessage(id, message);

        // then
        // 로그를 통해 각 리스너가 호출되는지 확인
        // 1, 2, 3, 4번 리스너 모두 호출되어야 함
        Thread.sleep(2000); // 비동기 리스너 대기
    }

    @Test
    void 롤백_이벤트_발행_테스트() throws InterruptedException {
        // given
        Long id = 2L;
        String message = "error";

        // when & then
        try {
            msgEventPubService.saveAndMessage(id, message);
        } catch (RuntimeException e) {
            // expected
        }

        // then
        // 1, 2번 리스너만 호출되고 3, 4번 리스너는 호출되지 않아야 함
        try {
            Thread.sleep(2000); // 비동기 리스너 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
