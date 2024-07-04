package dev.tudos.quizbuilder.common.api.dto;

/**
 * Enum for generic API errors.
 */
public enum GenericError implements ApiError {
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    BAD_REQUEST_MISSING_PARAMETER(400, "BAD_REQUEST"),
    BAD_REQUEST_ARGUMENT_TYPE_MISMATCH(400, "BAD_REQUEST"),
    BAD_REQUEST_MISSING_REQUEST_PART(400, "BAD_REQUEST"),
    FORBIDDEN(403, "FORBIDDEN"),
    UNSUPPORTED_MEDIA_TYPE(415, "UNSUPPORTED_MEDIA_TYPE"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED"),
    NOT_ACCEPTABLE(406, "NOT_ACCEPTABLE"),
    NOT_FOUND(404, "NOT_FOUND"),
    HANDLER_NOT_FOUND(404, "NOT_FOUND"),
    RESOURCE_NOT_FOUND(404, "NOT_FOUND"),
    ALREADY_EXISTS(400, "ALREADY_EXISTS"),
    RESOURCE_ALREADY_EXISTS(400, "ALREADY_EXISTS"),
    NAMED_RESOURCE_ALREADY_EXISTS(400, "ALREADY_EXISTS"),
    VALIDATION_ERROR(400, "BAD_REQUEST"),
    PARAMETRIZED_VALIDATION_ERROR(400, "BAD_REQUEST"),
    LARGE_MEDIA_SIZE(400, "LARGE_MEDIA_SIZE"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE"),
    NOT_IMPLEMENTED(501, "NOT_IMPLEMENTED");

    private final int statusCode;
    private final String errorCode;

    GenericError(int statusCode, String errorCode) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
