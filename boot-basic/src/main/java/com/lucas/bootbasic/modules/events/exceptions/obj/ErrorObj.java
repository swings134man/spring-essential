package com.lucas.bootbasic.modules.events.exceptions.obj;

import lombok.*;

/**
 * @package : com.lucas.bootbasic.modules.events.exceptions
 * @name : ErrorObj.java
 * @date : 2025. 9. 29. 오후 4:31
 * @author : lucaskang(swings134man)
 * @Description: Error Test Event Object
**/
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObj {

    private Long id;
    private String message;

}
