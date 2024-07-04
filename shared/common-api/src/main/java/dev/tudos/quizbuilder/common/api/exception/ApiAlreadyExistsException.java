package dev.tudos.quizbuilder.common.api.exception;

import dev.tudos.quizbuilder.common.api.dto.ApiResource;
import dev.tudos.quizbuilder.common.api.dto.GenericError;
import lombok.NonNull;

public final class ApiAlreadyExistsException extends ApiException {
    public ApiAlreadyExistsException() {
        super(GenericError.ALREADY_EXISTS);
    }

    public ApiAlreadyExistsException(@NonNull ApiResource resource) {
        super(GenericError.RESOURCE_ALREADY_EXISTS, resource.getName());
    }

    private ApiAlreadyExistsException(ApiResource apiResource, String parameter, Object value) {
        super(GenericError.NAMED_RESOURCE_ALREADY_EXISTS, capitalize(apiResource.getName()), parameter, value);
    }

    public static ApiAlreadyExistsException withParameterAndValue(ApiResource apiResource, String parameter,
                                                                  Object value) {
        return new ApiAlreadyExistsException(apiResource, parameter, value);
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
