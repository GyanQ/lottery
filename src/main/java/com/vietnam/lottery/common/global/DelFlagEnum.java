package com.vietnam.lottery.common.global;

public enum DelFlagEnum {

    CODE("0", "正常"),
    MESSAGE("1", "删除");

    private final String code;
    private final String message;

    DelFlagEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
