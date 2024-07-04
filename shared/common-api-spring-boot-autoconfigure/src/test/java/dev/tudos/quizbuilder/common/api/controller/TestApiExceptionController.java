package dev.tudos.quizbuilder.common.api.controller;

import dev.tudos.quizbuilder.common.api.dto.GenericError;
import dev.tudos.quizbuilder.common.api.exception.ApiAccessDeniedException;
import dev.tudos.quizbuilder.common.api.exception.ApiException;
import dev.tudos.quizbuilder.common.api.exception.ApiNotFoundException;
import dev.tudos.quizbuilder.common.api.exception.ApiValidationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/")
public class TestApiExceptionController {
    @GetMapping("/no-resource")
    public void handleNoResource() {
        throw new ApiNotFoundException(() -> "file");
    }

    @GetMapping("/access-denied")
    public void handleAccessDenied() {
        throw new ApiAccessDeniedException();
    }

    @GetMapping("/validation-exception")
    public void handleApiValidationException() {
        throw new ApiValidationException();
    }

    @GetMapping("/validation-exception-with-parameter")
    public void handleApiValidationExceptionWithParameter() {
        throw new ApiValidationException("parameter", "value");
    }

    @GetMapping("/runtime-exception")
    public void handleRuntimeException() {
        throw new RuntimeException("Some system details");
    }

    @GetMapping("/api-already-exists")
    public void handleAlreadyExists() {
        throw new ApiException(GenericError.ALREADY_EXISTS);
    }
}
