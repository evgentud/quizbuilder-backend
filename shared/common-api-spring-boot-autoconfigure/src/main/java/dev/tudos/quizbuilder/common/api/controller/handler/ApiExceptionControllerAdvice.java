package dev.tudos.quizbuilder.common.api.controller.handler;

import dev.tudos.quizbuilder.common.api.dto.ApiError;
import dev.tudos.quizbuilder.common.api.dto.FieldErrorData;
import dev.tudos.quizbuilder.common.api.dto.GenericError;
import dev.tudos.quizbuilder.common.api.dto.response.ApiErrorResponse;
import dev.tudos.quizbuilder.common.api.exception.ApiClientException;
import dev.tudos.quizbuilder.common.api.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Controller advice to handle exceptions and return appropriate error response.
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionControllerAdvice {
    private final MessageSource messageSource;

    /**
     * Handle API exception.
     *
     * @return ApiErrorResponse
     */
    @ExceptionHandler(ApiException.class)
    public ApiErrorResponse apiException(ApiException ex, HttpServletResponse response) {
        final int statusCode = Optional.ofNullable(ex.getApiError())
                .map(ApiError::getStatusCode)
                .orElse(HttpStatus.BAD_REQUEST.value());
        response.setStatus(statusCode);
        return buildLocalizedApiErrorResponseOrDefault(ex);
    }

    /**
     * Handle 400 errors intended for API clients (developers) as opposed to the ones intended for UI users.
     *
     * @param ex ApiClientException
     * @return ApiErrorResponse
     */
    @ExceptionHandler(ApiClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse apiException(ApiClientException ex) {
        return buildApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handle default DTO binding validation exception.
     *
     * @param ex MethodArgumentNotValidException thrown by Spring MVC
     * @return list errors ApiErrorResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBindingValidation(MethodArgumentNotValidException ex) {
        final Set<FieldErrorData> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorData(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(toSet());
        return buildApiErrorResponse(HttpStatus.BAD_REQUEST, getLocalizedMessage(GenericError.VALIDATION_ERROR),
                fieldErrors);
    }

    @ExceptionHandler({MultipartException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleGenericBadRequest(Exception ex) {
        final String message = Optional.ofNullable(ex.getCause())
                .map(Throwable::getMessage)
                .orElse(ex.getMessage());
        return buildApiErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        return buildLocalizedApiErrorResponse(GenericError.BAD_REQUEST_MISSING_PARAMETER, ex.getParameterName());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        return buildLocalizedApiErrorResponse(GenericError.BAD_REQUEST_MISSING_REQUEST_PART, ex.getRequestPartName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildLocalizedApiErrorResponse(GenericError.BAD_REQUEST_ARGUMENT_TYPE_MISMATCH, ex.getName());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMaxSizeException(MaxUploadSizeExceededException e) {
        return buildLocalizedApiErrorResponse(GenericError.LARGE_MEDIA_SIZE);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNoHandlerFound(NoHandlerFoundException ex) {
        return buildLocalizedApiErrorResponse(GenericError.HANDLER_NOT_FOUND, ex.getRequestURL());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNoResourceFound(NoResourceFoundException ex) {
        return buildLocalizedApiErrorResponse(GenericError.RESOURCE_NOT_FOUND, ex.getResourcePath());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiErrorResponse httpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        return buildLocalizedApiErrorResponse(GenericError.NOT_ACCEPTABLE, ex.getSupportedMediaTypes());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiErrorResponse httpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return buildLocalizedApiErrorResponse(GenericError.UNSUPPORTED_MEDIA_TYPE, ex.getSupportedMediaTypes());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiErrorResponse httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildLocalizedApiErrorResponse(GenericError.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleAccessDenied(AccessDeniedException ex) {
        return buildLocalizedApiErrorResponse(GenericError.FORBIDDEN);
    }

    /**
     * Handle UnsupportedOperationException.
     *
     * @param e UnsupportedOperationException
     * @return ApiErrorResponse DTO
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ApiErrorResponse handleUnsupportedOperationException(UnsupportedOperationException e,
                                                                HttpServletRequest request) {
        log.warn("Unsupported operation occurred while processing request: {}", request.getRequestURI(), e);
        return buildLocalizedApiErrorResponse(GenericError.NOT_IMPLEMENTED);
    }

    /**
     * Handle any other server error.
     *
     * @param e Exception
     * @return ApiErrorResponse DTO
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleAnyException(Exception e, HttpServletRequest request) {
        log.error("Unexpected error occurred while processing request: {}", request.getRequestURI(), e);
        return buildLocalizedApiErrorResponse(GenericError.INTERNAL_SERVER_ERROR);
    }

    private ApiErrorResponse buildLocalizedApiErrorResponseOrDefault(ApiException ex) {
        return Optional.ofNullable(ex.getApiError())
                .map(apiError -> buildLocalizedApiErrorResponse(apiError, ex.getMessageParameters()))
                .orElseGet(() -> buildApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    private ApiErrorResponse buildLocalizedApiErrorResponse(ApiError apiError, Object... args) {
        return new ApiErrorResponse(apiError.getStatusCode(), apiError.getErrorCode(),
                getLocalizedMessage(apiError, args));
    }

    private String getLocalizedMessage(ApiError apiError, Object... args) {
        return messageSource.getMessage(apiError.getMessageKey(), args, "Unexpected error",
                LocaleContextHolder.getLocale());
    }

    private static ApiErrorResponse buildApiErrorResponse(HttpStatus httpStatus, String message) {
        return new ApiErrorResponse(httpStatus.value(), httpStatus.name(), message);
    }

    private ApiErrorResponse buildApiErrorResponse(HttpStatus httpStatus, String message,
                                                   Collection<FieldErrorData> fieldErrors) {
        return new ApiErrorResponse(httpStatus.value(), httpStatus.name(), message, fieldErrors);
    }
}
