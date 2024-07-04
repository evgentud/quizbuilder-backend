package dev.tudos.quizbuilder.common.api.dto;

/**
 * Interface for API errors.
 * <p/>
 *     Implementations of this interface should be enums, where each enum value represents a specific error.
 *     The enum value should be the error description in uppercase, with underscores instead of spaces.
 *     The enum value should also implement the {@link ApiError} interface.
 *     The enum value should have a constructor with a single parameter of type {@link String}, which should be the error code.
 */
public interface ApiError {
    /**
     * Get HTTP status code of the API response returned with this error.
     *
     * @return HTTP status code
     */
    int getStatusCode();

    /**
     * Get the error code to be returned in the API response.
     * The error code should be a unique identifier of the error.
     *
     * @return error code
     */
    String getErrorCode();

    /**
     * Get the message key to be used for localizing the error message.
     * The message key should be unique and should be used to look up the error message in the localization resources.
     * The recommended format of the key is domain model-based prefix + dot + error description
     * (the string representation of enum value). Example: <code>USER.BANNED</code>
     *
     * @return message key
     */
    default String getMessageKey() {
        return this.toString();
    }
}
