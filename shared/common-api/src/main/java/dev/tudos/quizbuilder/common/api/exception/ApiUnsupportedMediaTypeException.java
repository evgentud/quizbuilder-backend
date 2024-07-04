package dev.tudos.quizbuilder.common.api.exception;

import dev.tudos.quizbuilder.common.api.dto.GenericError;

public final class ApiUnsupportedMediaTypeException extends ApiException {
    public ApiUnsupportedMediaTypeException(String mediaType) {
        super(GenericError.UNSUPPORTED_MEDIA_TYPE, mediaType);
    }
}
