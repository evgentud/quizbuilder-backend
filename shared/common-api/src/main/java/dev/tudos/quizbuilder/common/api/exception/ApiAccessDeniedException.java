package dev.tudos.quizbuilder.common.api.exception;

import dev.tudos.quizbuilder.common.api.dto.GenericError;

public final class ApiAccessDeniedException extends ApiException {
    public ApiAccessDeniedException() {
        super(GenericError.FORBIDDEN);
    }
}
