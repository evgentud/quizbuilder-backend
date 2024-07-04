package dev.tudos.quizbuilder.common.api.exception;

import dev.tudos.quizbuilder.common.api.dto.ApiResource;
import dev.tudos.quizbuilder.common.api.dto.GenericError;
import lombok.NonNull;

/**
 * Exception to be thrown when a requested API resource is not found.
 */
public final class ApiNotFoundException extends ApiException {
    /**
     * Constructor for resource not found errors.
     *
     * @param resource The resource that was not found.
     */
    public ApiNotFoundException(@NonNull ApiResource resource) {
        super(GenericError.RESOURCE_NOT_FOUND, resource.getName());
    }

    /**
     * Constructor for generic resource not found errors. Not recommended for use.
     * Use {@link ApiNotFoundException#ApiNotFoundException(ApiResource)} instead.
     */
    public ApiNotFoundException() {
        super(GenericError.NOT_FOUND);
    }
}
