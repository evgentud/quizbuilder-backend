package dev.tudos.quizbuilder.common.api.exception;

import dev.tudos.quizbuilder.common.api.dto.GenericError;

public final class ApiUnauthorizedException extends ApiException {
    public ApiUnauthorizedException() {
        super(GenericError.UNAUTHORIZED);
    }
}
