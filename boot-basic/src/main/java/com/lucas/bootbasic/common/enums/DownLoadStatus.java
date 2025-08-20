package com.lucas.bootbasic.common.enums;

public enum DownLoadStatus {

    WAITING("w"),
    PROCESSING("p"),
    DONE("d");

    final String code;

    DownLoadStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DownLoadStatus getByCode(String code) {
        for(DownLoadStatus c : values()) {
            if(c.code.equals(code)) return c;
        }
        return null;
    }
}
