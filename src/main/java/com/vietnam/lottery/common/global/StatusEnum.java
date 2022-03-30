package com.vietnam.lottery.common.global;

public enum StatusEnum {

    WAIT_PAY("1", "待支付"),
    FINISH_PAY("2", "已支付"),
    CANCEL_PAY("3", "取消支付");

    private final String code;
    private final String message;

    StatusEnum(String code, String message) {
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
