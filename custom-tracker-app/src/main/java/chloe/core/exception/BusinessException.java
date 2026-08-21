package chloe.core.exception;

import chloe.core.payload.ResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;
    private String detail;

    private BusinessException(ResponseCode responseCode) {

        super(responseCode.getMessage());

        this.responseCode = responseCode;
    }

    private BusinessException(ResponseCode responseCode, String detail) {

        this(responseCode);
        this.detail = detail;
    }

    public static BusinessException from(ResponseCode responseCode) {

        return new BusinessException(responseCode);
    }

    public static BusinessException of(ResponseCode responseCode, String detail) {

        return new BusinessException(responseCode, detail);
    }
}
