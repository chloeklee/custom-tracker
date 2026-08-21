package chloe.core.payload;

import lombok.Getter;

@Getter
public enum ResponseCode {

    OK(0, "OK"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error", 500)
    ;

    private final int code;
    private final String message;
    private int errorCode;

    ResponseCode(int code, String message) {

        this.code = code;
        this.message = message;
    }

    ResponseCode(int code, String message, int errorCode) {

        this(code, message);
        this.errorCode = errorCode;
    }

    public boolean isError() {

        return errorCode != 0;
    }
}
