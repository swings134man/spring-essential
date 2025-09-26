package com.lucas.bootbasic.modules.events;

import lombok.Getter;
import lombok.Setter;

/**
 * @package : com.lucas.bootbasic.modules.events
 * @name : MessageEventObj.java
 * @date : 2025. 9. 26. 오후 4:14
 * @author : lucaskang(swings134man)
 * @Description: 발행할 이벤트 객체
**/
@Getter @Setter
public class MessageEventObj {

    private Long id;
    private String message;
}
