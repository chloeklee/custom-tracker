package chloe.core.exception;

import chloe.core.payload.ApiResponse;
import chloe.core.payload.ResponseCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> exception(BusinessException e, HttpServletResponse response) {

        if (e.getResponseCode().isError()) {

            response.setStatus(e.getResponseCode().getErrorCode());
        }

        return ApiResponse.of(e.getResponseCode());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<List<String>> exception(Exception e, HttpServletResponse response) {

        int code = e instanceof ErrorResponse errorResponse ?
                errorResponse.getStatusCode().value()
                : ResponseCode.INTERNAL_SERVER_ERROR.getCode();

        response.setStatus(code);

        return ApiResponse.error(
                code,
                e.getMessage(),
                Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList()
        );
    }
}
