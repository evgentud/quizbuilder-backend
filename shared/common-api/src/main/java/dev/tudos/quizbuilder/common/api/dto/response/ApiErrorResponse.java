package dev.tudos.quizbuilder.common.api.dto.response;

import dev.tudos.quizbuilder.common.api.dto.FieldErrorData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString
public class ApiErrorResponse {
    private int statusCode;
    private String errorCode;
    private String message;
    private Collection<FieldErrorData> fieldErrors;

    public ApiErrorResponse(int statusCode, String errorCode, String message, Collection<FieldErrorData> fieldErrors) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public ApiErrorResponse(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
