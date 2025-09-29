package com.lucas.bootbasic.modules.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @package : com.lucas.bootbasic.modules.events
 * @name : MessageEventHandler.java
 * @date : 2025. 9. 26. 오후 4:16
 * @author : lucaskang(swings134man)
 * @Description: 발행한 이벤트를 처리하는 Listener
 * - 기본적으로 EventListener 는 동기방식 이며, 파라미터로 들어오는 객체 타입에 따라 자동으로 핸들러가 매핑됨 -> !!!!같은 타입을 받는 Listener 가 여러개라면 모두 호출됨!!!!
 * - 2가지 종류의 Listener Type 존재(EventListener, TransactionalEventListener) + Async 를 조합 하면 총 4가지 조합 가능
 * @EventListener(condition = "#event.async == false") -> 방식으로 어떤 이벤트를 처리할지 조건 지정 가능
**/
@Component
@RequiredArgsConstructor
@Slf4j
public class MessageEventListener {


    // 1. Basic Event Listener (동기) @EventListener
    @EventListener
    public void handleSyncMsg(MessageEventObj event) {
        log.info("1. Sync Listener Called - ID: {}, Message: {}", event.getId(), event.getMessage());
    }

    // 2. Async Event Listener (비동기) @EventListener + @Async
    // 별도의 Thread 에서 실행되기에 Caller 측의 Transaction 과는 별개로 동작하게 됨. 따라서 Caller 측이 Rollback 되더라도 이 Listener 는 영향을 받지 않음.
    @Async("taskExecutor")
    @EventListener
    public void handleAsyncMsg(MessageEventObj event) {
        log.info("2. Async Listener Called - ID: {}, Message: {}", event.getId(), event.getMessage());
    }


    // --------------------------------------------------------------------------------------------------------------

    // 3. Transactional Event Listener (동기) @TransactionalEventListener
    // Caller 측 Transaction 이 Commit 된 이후에 실행됨. 따라서 Caller 측이 Rollback 되면 이 Listener 는 호출되지 않음.
    // phase option 을 통해 언제 호출될지 지정 가능 (기본값: AFTER_COMMIT)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void syncTrHandle(MessageEventObj event) {
        // if Caller Transaction is Rollback, this Listener will not be called.
        log.info("3. Sync Transactional Listener Called - ID: {}, Message: {}", event.getId(), event.getMessage());
    }


    // 4. Async Transactional Event Listener (비동기) @TransactionalEventListener + @Async
    // 별도의 Thread 에서 실행되기에 Caller 측의 Transaction 과는 별개로 동작하게 됨. 따라서 Caller 측이 Rollback 되더라도 이 Listener 는 영향을 받지 않음.
    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    @Transactional(propagation = Propagation.REQUIRES_NEW) // @TransactionalEventListener 와 함께 사용 시 별도의 트랜잭션 컨텍스트에서 실행됨 -> Rollback 시 Caller 측과는 별개로 동작
    // Async 사용시 트랜잭션이 분리만 될뿐 DB I/O 작업이 있다면 트랜잭션 처리는 필요함! -> @Transactional 사용 -> 안하면 트랜잭션 롤백 동작 X
    // TransactionalEventListener 는 REQUIRES_NEW 를 사용하여야 함.
    public void asyncTrHandle(MessageEventObj event) {
        // if Caller Transaction is Rollback, this Listener will not be called.
        log.info("4. Async Transactional Listener Called - ID: {}, Message: {}", event.getId(), event.getMessage());
    }

}
