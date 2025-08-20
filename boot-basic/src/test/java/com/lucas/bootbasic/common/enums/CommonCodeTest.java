package com.lucas.bootbasic.common.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonCodeTest {

    @Test
    @DisplayName("CommonCode Enum Test")
    void commonCodeEnumTest() {
        // Enum 의 Value(Code) 출력
        String key = CommonCode.SUCCESS.getCode();

        // Enum 의 Value(Message) 출력
        String value = CommonCode.FAIL.getMessage();

        // Enum 의 키워드 자체 출력
        String keyword = CommonCode.SUCCESS.name();

        // Message 값 가져오기 by code
        String message = CommonCode.getMessageByCode("W");

        System.out.println("CommonCode Key: " + key);
        System.out.println("CommonCode Value: " + value);
        System.out.println("CommonCode Keyword: " + keyword);
        System.out.println("CommonCode Message by Code: " + message);
    }



}
