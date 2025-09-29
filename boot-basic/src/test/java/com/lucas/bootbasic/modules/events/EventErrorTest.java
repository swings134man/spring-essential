package com.lucas.bootbasic.modules.events;

import com.lucas.bootbasic.modules.events.exceptions.obj.AfterObj;
import com.lucas.bootbasic.modules.events.exceptions.ErrorEntity;
import com.lucas.bootbasic.modules.events.exceptions.obj.AsyncObj;
import com.lucas.bootbasic.modules.events.exceptions.obj.BeforeObj;
import com.lucas.bootbasic.modules.events.exceptions.obj.ErrorObj;
import com.lucas.bootbasic.modules.events.exceptions.ErrorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

/**
 * @package : com.lucas.bootbasic.modules.events
 * @name : EventErrorTest.java
 * @date : 2025. 9. 29. 오후 4:49
 * @author : lucaskang(swings134man)
 * @Description: Event Listener Exception propagate(전파) Test
**/
@SpringBootTest
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EventErrorTest {

    @Autowired
    MsgEventPubService msgEventPubService;

    @Autowired
    ErrorRepository errorRepository;

    @BeforeEach
    void setUp() {
        errorRepository.deleteAllInBatch();
    }


    @Test
    @DisplayName("Event Listener Rollback true 테스트")
    void BASIC_리스너_예외_전파_테스트() throws Exception {
        // given
        ErrorObj obj = new ErrorObj(null, "Basic");

        // when & then
        try {
            msgEventPubService.errorTestSave(obj);
        } catch (RuntimeException e) {
            log.error("Exception caught in test: {}", e.getMessage());
        }

        // then
        Assertions.assertEquals(0, errorRepository.count());
        List<ErrorEntity> all = errorRepository.findAll();
        System.out.println(all);
    }

    @Test
    @DisplayName("(After Commit)Tr Listener Rollback false 테스트")
    void TR_리스너_예외_전파_없음_테스트() throws Exception {
        // given
        AfterObj obj = new AfterObj(null, "NoRollback");

        // when & then
        try {
            msgEventPubService.errorTestTrAfterCommitSave(obj);
        } catch (RuntimeException e) {
            log.error("Exception caught in test: {}", e.getMessage());
        }

        // then
        Assertions.assertEquals(1, errorRepository.count());
        List<ErrorEntity> all = errorRepository.findAll();
        System.out.println(all);
    }

    @Test
    @DisplayName("(Before Commit)Tr Listener Rollback true 테스트 - 트랜잭션 커밋 전 예외 발생")
    void TR_BEFORE_리스너_예외_전파_테스트() throws Exception {
        // given
        BeforeObj obj = new BeforeObj(null, "BeforeCommit");

        // when & then
        try {
            msgEventPubService.errorTestTrBeforeCommitSave(obj);
        } catch (RuntimeException e) {
            log.error("Exception caught in test: {}", e.getMessage());
        }

        // then
        Assertions.assertEquals(0, errorRepository.count());
        List<ErrorEntity> all = errorRepository.findAll();
        System.out.println(all);
    }

    @Test
    @DisplayName("(Async + After Commit)Tr Listener Rollback false 테스트 - 별도 트랜잭션에서 실행")
    void TR_ASYNC_리스너_예외_전파_없음_테스트() throws Exception {
        // given
        AsyncObj obj = new AsyncObj(null, "기본 객체");

        // when & then
        try {
            msgEventPubService.asyncTrTest(obj);
            // Async 이므로 잠시 대기
            Thread.sleep(2000);
        } catch (RuntimeException e) {
            log.error("Exception caught in test: {}", e.getMessage());
        }

        // then
        Assertions.assertEquals(1, errorRepository.count());
        List<ErrorEntity> all = errorRepository.findAll();
        System.out.println(all);
    }

}
