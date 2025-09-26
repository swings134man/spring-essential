package com.lucas.bootbasic.modules.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @package : com.lucas.bootbasic.modules.events
 * @name : EventPublisherService.java
 * @date : 2025. 9. 26. 오후 4:13
 * @author : lucaskang(swings134man)
 * @Description: 이벤트를 Trigger 하는 서비스
**/
@Service
@RequiredArgsConstructor
@Slf4j
public class MsgEventPubService {

    // 이벤트 발행 Publisher(Spring Container Object)
    private final ApplicationEventPublisher eventPublisher;


    /**
    * @methodName : saveAndMessage
    * @date : 2025. 9. 26. 오후 4:19
    * @author : lucaskang
    * @Description: 시나리오: Entity 객체를 저장 후 비동기로 이벤트 발행(Message 전송) 이라는 가상의 시나리오
    **/
    @Transactional
    public void saveAndMessage(Long id, String message) throws InterruptedException {
        // 1. DB Save
        log.info(">>>> 1. DB Save ...");
        Thread.sleep(1000);



        // 2. Event Publish(Message 전송)
        log.info(">>>> 2. Event Publish(Message 전송) ...");
        MessageEventObj msgObj = new MessageEventObj();
        msgObj.setId(id);
        msgObj.setMessage(message);

        eventPublisher.publishEvent(msgObj); // 이벤트 발행

        // 2-1. message 가 "error" 라면 강제로 Exception 발생시켜서 Transaction Rollback 처리
        if("error".equalsIgnoreCase(message)) {
            log.error(">>> Simulated Error Occurred! Transaction Rollback Triggered.");
            throw new RuntimeException("Simulated Error: Transaction Rollback Triggered.");
        }
    }


}//class
