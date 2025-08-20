package com.lucas.bootbasic.common.enums;

import lombok.Getter;

@Getter
public enum CommonCode {

    // 성공
    SUCCESS("S", "성공"),
    // 실패
    FAIL("F", "실패"),
    // 오류
    ERROR("E", "오류"),
    // 경고
    WARNING("W", "경고");

    private final String code;
    private final String message;

    CommonCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(String code) {
        for (CommonCode commonCode : values()) {
            if (commonCode.getCode().equals(code)) {
                return commonCode.getMessage();
            }
        }
        return null; // or throw an exception if preferred
    }
}
