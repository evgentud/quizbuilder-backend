package dev.tudos.quizbuilder.common.api.exception;

import dev.tudos.quizbuilder.common.api.dto.GenericError;

/**
 * Exception to be thrown when a validation error occurs of input data.
 */
public final class ApiValidationException extends ApiException {
    /**
     * Constructor for generic validation errors. Not recommended for use.
     * Use {@link ApiValidationException#ApiValidationException(String, Object)} instead.
     */
    public ApiValidationException() {
        super(GenericError.VALIDATION_ERROR);
    }

    /**
     * Constructor for parameterized validation errors.
     *
     * @param parameter The name of the parameter that failed validation.
     * @param value     The value of the parameter that failed validation.
     */
    public ApiValidationException(String parameter, Object value) {
        super(GenericError.PARAMETRIZED_VALIDATION_ERROR, parameter, value);
    }
}
