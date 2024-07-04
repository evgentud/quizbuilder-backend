package dev.tudos.quizbuilder.common.api.exception;

import lombok.NonNull;

public class ApiClientException extends RuntimeException {
    public ApiClientException(@NonNull String message) {
        super(message);
    }
}
