package com.lucas.bootbasic.modules.events.exceptions.obj;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BeforeObj {
    private Long id;
    private String message;
}
