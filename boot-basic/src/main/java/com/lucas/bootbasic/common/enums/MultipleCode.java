package com.lucas.bootbasic.common.enums;

public enum MultipleCode {

    SAMPLE(new String[] { "A", "B", "C" }),
    TEST(new String[] { "X", "Y" });

    private final String[] values;

    MultipleCode(String[] values) {
        this.values = values;
    }

    // getValue(0) == A
    public String getValue(int index) {
        return values[index];
    }

    // getValues() == { "A", "B", "C" }
    public String[] getValues() {
        return values;
    }

    // fromValue("A") == SAMPLE
    public static MultipleCode fromValue(String value) {
        for (MultipleCode code : MultipleCode.values()) {
            for (String v : code.getValues()) {
                if (v.equals(value)) {
                    return code;
                }
            }
        }
        return null; // 없으면 null 반환
    }

}
