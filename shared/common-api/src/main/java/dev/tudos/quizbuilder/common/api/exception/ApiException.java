package dev.tudos.quizbuilder.common.api.exception;

import dev.tudos.quizbuilder.common.api.dto.ApiError;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Arrays;

/**
 * Exception to be thrown when an error occurs in the API or business logic.
 * <p/>
 *     The exception is caught by the global exception handler and translated into an API response.
 */
@Getter
@ToString
public class ApiException extends RuntimeException {
    protected ApiError apiError;
    protected Object[] messageParameters;

    /**
     * Convenience constructor for throwing ApiException without localization support.
     * This is not the recommended way to use this API and can be subject to removal later.
     *
     * @param message The non-localized error message
     */
    public ApiException(@NonNull String message) {
        super(message);
    }

    /**
     * Constructor supporting localization of error messages.
     *
     * @param apiError          An enum implementation of ApiError interface.
     *                          Usually each microservice should define one implementation per domain model and define
     *                          one enum value per error message.
     * @param messageParameters messageParameters to be resolved by index-based placeholders in messages.properties
     *                          dictionaries ({0}, {1} etc)
     */
    public ApiException(@NonNull ApiError apiError, Object... messageParameters) {
        super("ApiException{" + "apiError=" + apiError + ", messageParameters=" + Arrays.toString(messageParameters) + '}');
        this.apiError = apiError;
        this.messageParameters = messageParameters;
    }
}
