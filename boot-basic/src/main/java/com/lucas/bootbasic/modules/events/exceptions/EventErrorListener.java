package com.lucas.bootbasic.modules.events.exceptions;

import com.lucas.bootbasic.modules.events.exceptions.obj.AfterObj;
import com.lucas.bootbasic.modules.events.exceptions.obj.BeforeObj;
import com.lucas.bootbasic.modules.events.exceptions.obj.ErrorObj;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @package : com.lucas.bootbasic.modules.events
 * @name : EventErrorListener.java
 * @date : 2025. 9. 29. 오후 4:30
 * @author : lucaskang(swings134man)
 * @Description: Exception 발생 테스트용 Listener
**/
@Component
@RequiredArgsConstructor
@Slf4j
public class EventErrorListener {


    // Exception 발생시, Callee 에 예외 전파
    // Async 사용시, 트랜잭션 경계 분리로 인해 Callee 에 예외 전파 안됨.
    @EventListener
    public void basicEventListener(ErrorObj obj) {
        log.info(">> Basic Event Listener Called");
        throw new RuntimeException("Basic Event Listener");
    }

    // Callee 트랜잭션 커밋 전에 호출됨. 따라서 이 시점에 예외가 발생하면 트랜잭션이 롤백됨.
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void trBeforeCommit(BeforeObj obj) {
        log.info(">> Before Commit -- Transaction Listener Called");
        throw new RuntimeException("Before Commit Event Listener");
    }


    // Callee 트랜잭션 커밋 후에 호출됨. 따라서 이 시점에 예외가 발생해도 트랜잭션에는 영향이 없음.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void trAfterCommit(AfterObj obj) {
        log.info(">> After Commit -- Transaction Listener Called");
        throw new RuntimeException("After Commit Event Listener");
    }
}
