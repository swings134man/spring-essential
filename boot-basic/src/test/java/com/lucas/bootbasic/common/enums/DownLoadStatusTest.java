package com.lucas.bootbasic.common.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DownLoadStatusTest {

    @Test
    @DisplayName("DownLoadStatus Enum Test")
    void downLoadStatusEnumTest() {
        // Enum 은 toString() 메소드가 자동으로 구현되어 있어, Enum 의 이름을 출력함.
        DownLoadStatus waiting = DownLoadStatus.WAITING;
        System.out.println("Waiting: " + waiting);
    }


}
