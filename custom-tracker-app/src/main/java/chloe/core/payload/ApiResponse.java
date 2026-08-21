package chloe.core.payload;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private T response;

    private ApiResponse(int code, String message) {

        this.code = code;
        this.message = message;
    }

    private ApiResponse(int code, String message, T response) {

        this(code, message);
        this.response = response;
    }

    private ApiResponse(ResponseCode responseCode, T response) {

        this(responseCode.getCode(), responseCode.getMessage(), response);
    }

    public static ApiResponse<Void> of(ResponseCode responseCode) {

        return new ApiResponse<>(responseCode, null);
    }

    public static <T> ApiResponse<T> of(ResponseCode responseCode, T response) {

        return new ApiResponse<>(responseCode, response);
    }

    public static ApiResponse<Void> ok() {

        return ApiResponse.of(ResponseCode.OK);
    }

    public static <T> ApiResponse<T> ok(T response) {

        return ApiResponse.of(ResponseCode.OK, response);
    }

    public static <T> ApiResponse<T> error(int code, String message, T response) {

        return new ApiResponse<>(code, message, response);
    }
}
