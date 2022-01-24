package com.vietnam.lottery.common.global;

public class GlobalException extends RuntimeException {
    public GlobalException() {
        super();
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(Throwable cause) {
        super(cause);
    }
}
