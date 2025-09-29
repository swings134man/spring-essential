package com.lucas.bootbasic.rest;

import com.lucas.bootbasic.modules.events.MessageEventObj;
import com.lucas.bootbasic.modules.events.MsgEventPubService;
import com.lucas.bootbasic.modules.events.exceptions.obj.ErrorObj;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package : com.lucas.bootbasic.rest
 * @name : EventController.java
 * @date : 2025. 9. 26. 오후 4:22
 * @author : lucaskang(swings134man)
 * @Description: Event Test 용 Controller @See: modules/events/...
**/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/events")
public class EventController {

    private final MsgEventPubService service;

    @PostMapping
    public ResponseEntity<String> callMsgService(@RequestBody MessageEventObj event) throws InterruptedException {
        service.saveAndMessage(event.getId(), event.getMessage());
        return ResponseEntity.ok("Success");
    }

//    @PostMapping("/error")
//    public void errorTestCall(@RequestBody ErrorObj obj) {
//
//    }

}
